package br.com.rjguastalli.v1.voto;

import org.springframework.util.ObjectUtils;

public enum VotoAssociadoEnum {

    CONTRA(0, "NAO"),
    A_FAVOR(1, "SIM");

    private final Integer cod;
    private final String value;

    VotoAssociadoEnum(Integer cod, String value) {
        this.cod = cod;
        this.value = value;
    }

    public static String toEnum(Integer cod) {
        if (ObjectUtils.isEmpty(cod)) return null;
        for (VotoAssociadoEnum votoAssociadoEnum : VotoAssociadoEnum.values()) {
            if(cod == votoAssociadoEnum.getCod()){
                return votoAssociadoEnum.getValue();
            }
        }
        return null;
    }

    public int getCod() {
        return cod;
    }

    public String getValue() {
        return value;
    }
}
