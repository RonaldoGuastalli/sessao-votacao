package br.com.rjguastalli.voto.service;

import br.com.rjguastalli.GenericException;
import br.com.rjguastalli.model.UserInfoEnum;
import br.com.rjguastalli.model.UserInfoResponse;
import br.com.rjguastalli.restclient.UserInfoRestClient;
import br.com.rjguastalli.sessao.repository.entity.SessaoEntity;
import br.com.rjguastalli.voto.repository.VotoAssociadoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegraVotoAssociadoService {

    private VotoAssociadoRepository votoAssociadoRepository;
    private UserInfoRestClient userInfoRestClient;


    public void verificarCpfValido(String cpf) {
        UserInfoResponse userInfoResponse = userInfoRestClient.buscarUsuario(cpf);
        if (UserInfoEnum.UNABLE_TO_VOTE.equals(userInfoResponse.getResult())) {
            throw new GenericException("Este CPF não está inválido ou não é apto para votar nesta sessão.",
                    HttpStatus.BAD_REQUEST);
        }

        if (UserInfoEnum.SOME_EXCEPTION_OCCURRED.equals(userInfoResponse.getResult())) {
            throw new GenericException("Impossível prosseguir a votação para este CPF.",
                    HttpStatus.BAD_REQUEST);
        }
    }

    public void sessaoEstaNaPauta(SessaoEntity sessaoEntity, Long pautaId) {
        if (!pautaId.equals(sessaoEntity.getPauta().getId())) {
            throw new GenericException("Esta sessão não consta para esta pauta.", HttpStatus.BAD_REQUEST);
        }
    }

    public void dataDaSessaoValidaParaVotacao(SessaoEntity sessaoEntity) {
        if (LocalDateTime.now().isAfter(sessaoEntity.getDataTermino()) ||
                LocalDateTime.now().isBefore(sessaoEntity.getDataAbertura())) {
            throw new GenericException("Esta sessão esta com o perído inválido (data abertura: "
                    .concat(sessaoEntity.getDataAbertura().toString()
                            .concat(" data fechamento: ").concat(sessaoEntity.getDataTermino().toString()))
                    , HttpStatus.BAD_REQUEST);
        }
    }

    public void verificaAssociadoJaVotou(Long sessaoId, String cpfAssociado) {
        boolean jaVotou = votoAssociadoRepository.existsBySessaoIdAndCpfAssociado(sessaoId, cpfAssociado);
        if (jaVotou) {
            throw new GenericException("Este CPF: "
                    .concat(cpfAssociado)
                    .concat(" já votou nesta sessão e seu voto já foi computado"), HttpStatus.BAD_REQUEST);
        }
    }


}
