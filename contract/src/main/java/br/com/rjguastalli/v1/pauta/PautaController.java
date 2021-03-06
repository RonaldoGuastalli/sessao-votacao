package br.com.rjguastalli.v1.pauta;

import br.com.rjguastalli.GenericException;
import br.com.rjguastalli.handler.response.ErrorInfo;
import br.com.rjguastalli.v1.pauta.facade.PautaContractFacade;
import br.com.rjguastalli.v1.pauta.model.request.PautaRequest;
import br.com.rjguastalli.v1.pauta.model.response.PautaResponse;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;


@RestController
@RequestMapping("/v1/pauta")
@Api("Serviço para operações na pauta")
@AllArgsConstructor
public class PautaController {

    private PautaContractFacade pautaContractFacade;

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Criar uma nova pauta", response = PautaResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PautaResponse.class),
            @ApiResponse(code = 400, message = "Parametros inválidos", response = ErrorInfo.class),
            @ApiResponse(code = 404, message = "Erro pauta não encontrado", response = ErrorInfo.class),
            @ApiResponse(code = 500, message = "Erro interno na aplicação", response = ErrorInfo.class)
    })
    public ResponseEntity<PautaResponse> criarPauta(@ApiParam(value = "Assunto da pauta.", required = true)
                                                    @Validated @RequestBody PautaRequest pautaRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pautaContractFacade.criarNovaPauta(pautaRequest));
    }

    @GetMapping(path = "/{pauta_id}/pontuacao", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiOperation(value = "Buscar pauta a partir de um idenficador e apresentar sua pontuação.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PautaResponse.class),
            @ApiResponse(code = 400, message = "Parametros inválidos", response = ErrorInfo.class),
            @ApiResponse(code = 404, message = "Pauta não encontrada", response = GenericException.class),
            @ApiResponse(code = 500, message = "Erro interno na aplicação", response = ErrorInfo.class)
    })
    public ResponseEntity<PautaResponse> buscarPauta(@ApiParam(value = "pauta_id", required = true)
                                                     @PathVariable("pauta_id") Long pautaId) {
        return ResponseEntity.ok(pautaContractFacade.buscarPontuacaoPauta(pautaId));
    }

}
