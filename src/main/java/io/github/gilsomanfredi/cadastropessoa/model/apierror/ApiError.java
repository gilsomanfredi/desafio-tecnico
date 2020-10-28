package io.github.gilsomanfredi.cadastropessoa.model.apierror;

import io.github.gilsomanfredi.cadastropessoa.config.exception.CustomExceptionHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ApiError {

    private HttpStatus status;

    private LocalDateTime date;

    private String message;

    private List<ApiFieldError> errors;

    public ApiError(HttpStatus status, LocalDateTime date, String message) {
        this.status = status;
        this.date = date;
        this.message = message;
    }

    public void addError(ApiFieldError apiFieldError) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(apiFieldError);
    }
}
