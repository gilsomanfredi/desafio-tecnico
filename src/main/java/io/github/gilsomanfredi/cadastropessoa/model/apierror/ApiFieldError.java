package io.github.gilsomanfredi.cadastropessoa.model.apierror;

import lombok.Getter;

@Getter
public class ApiFieldError {

    String field;
    String error;

    public ApiFieldError(String field, String error) {
        this.field = field;
        this.error = error;
    }
}
