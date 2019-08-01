package com.example.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = false)
public class GeneralException extends RuntimeException implements FunctionalException {

    private Integer errorCode;
    private String message;
    private String documentationUrl;

    public GeneralException(String message) {
        super();
        this.errorCode = Math.abs(Objects.hashCode(GeneralException.class.getSimpleName() + ":" + message));
        this.message = message;
        this.documentationUrl = null;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
