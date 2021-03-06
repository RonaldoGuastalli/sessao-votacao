package br.com.rjguastalli.voto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VotoAssociadoModel {

    private String cpf;
    private Integer voto;
    private Long pautaId;
    private Long sessaoId;

}
