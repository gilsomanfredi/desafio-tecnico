package io.github.gilsomanfredi.cadastropessoa.config.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.gilsomanfredi.cadastropessoa.config.i18n.I18n;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private final @NonNull
    I18n i18n;
 
    @ExceptionHandler(value = { ValidacaoException.class })
    protected ResponseEntity<Object> handleValidacao(ValidacaoException ex, WebRequest request) {

    	var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var apiError = new ApiError(status, LocalDateTime.now(), i18n.getMessage(ex.getMessage(), ex.getArgs()));
    	
    	return handleExceptionInternal(ex, apiError, new HttpHeaders(), 
    			HttpStatus.BAD_REQUEST, request);
    }
 
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleGeneric(Exception ex, WebRequest request) {
    	
    	var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var apiError = new ApiError(status, LocalDateTime.now(), i18n.getMessage("houve.erro.servidor"));
    	
    	return handleExceptionInternal(ex, apiError, new HttpHeaders(), 
    			status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        var apiError = new ApiError(status, LocalDateTime.now(), i18n.getMessage("mensagem.validacao.dados"));

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String message = i18n.getMessage(error);
            apiError.addError(new ApiFieldError(error.getField(), message));
        }

        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            String message = i18n.getMessage(error);
            apiError.addError(new ApiFieldError(error.getObjectName(), message));
        }

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Getter
    static class ApiError {
        HttpStatus status;
        LocalDateTime date;
        String message;
        List<ApiFieldError> errors;

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

    @Getter
    static class ApiFieldError {
        String field;
        String error;

        public ApiFieldError(String field, String error) {
            this.field = field;
            this.error = error;
        }
    }
}
