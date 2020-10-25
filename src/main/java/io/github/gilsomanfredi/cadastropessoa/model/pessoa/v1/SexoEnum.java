package io.github.gilsomanfredi.cadastropessoa.model.pessoa.v1;

import java.util.stream.Stream;

import lombok.Getter;

/**
 * Enum para tipos de sexo.
 *
 * @author Gilso Manfredi
 * @since (27/07/2020)
 */
public enum SexoEnum {

    MASCULINO("M"),
    FEMININO("F");

    @Getter
    private final String codigo;

    SexoEnum(String codigo) {
        this.codigo = codigo;
    }

    public static SexoEnum valueOfByCodigo(String codigo) {
        return Stream.of(values())
                .filter(value -> value.getCodigo().equals(codigo)).findFirst().orElse(null);
    }
}
