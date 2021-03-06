package br.com.rjguastalli.voto.service;

import br.com.rjguastalli.sessao.service.SessaoService;
import br.com.rjguastalli.voto.model.VotoAssociadoModel;
import br.com.rjguastalli.voto.repository.entity.VotoAssociadoEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@AllArgsConstructor
@Component
public class VotoAssociadoDomainFacade {

    private VotoAssociadoService votoAssociadoService;
    private SessaoService sessaoService;
    private RegraVotoAssociadoService regraVotoAssociadoService;

    public void computarVoto(VotoAssociadoModel votoAssociadoModel) {
        var sessaoEntity = sessaoService.buscarSessao(votoAssociadoModel.getSessaoId());
        regraVotoAssociadoService.verificarCpfValido(votoAssociadoModel.getCpf());
        regraVotoAssociadoService.verificaAssociadoJaVotou(votoAssociadoModel.getSessaoId(), votoAssociadoModel.getCpf());
        regraVotoAssociadoService.sessaoEstaNaPauta(sessaoEntity, votoAssociadoModel.getPautaId());
        regraVotoAssociadoService.dataDaSessaoValidaParaVotacao(sessaoEntity);
        votoAssociadoService.computarVoto(votoAssociadoModel, sessaoEntity);
    }


    public List<VotoAssociadoEntity> buscarVotoDaSessaoDaPautaEspecifica(Long pautaId) {
        return votoAssociadoService.buscarVotosDaSessaoParaAPautaEspecifica(pautaId);
    }
}
