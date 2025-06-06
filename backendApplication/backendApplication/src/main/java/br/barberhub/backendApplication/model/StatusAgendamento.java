package br.barberhub.backendApplication.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusAgendamento {
	AGENDADA("AGENDADA"),
    CANCELADA("CANCELADA"),
    CONCLUIDA("CONCLUIDA");

    private String value;

    StatusAgendamento(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static StatusAgendamento fromValue(String value) {
        for (StatusAgendamento status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
