package io.github.gilsomanfredi.cadastropessoa.model.apierror;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiFieldError {

    private String field;
    private String error;

}
