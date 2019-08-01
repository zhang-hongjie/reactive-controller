//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.config;

import java.util.StringJoiner;
import org.springframework.http.HttpStatus;

public class TechnicalException extends RuntimeException implements FunctionalException {
    private String message;

    public TechnicalException(Throwable cause) {
        super(cause);
        this.message = cause.getMessage() == null ? cause.toString() : cause.getMessage();
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public String toString() {
        return (new StringJoiner(", ", TechnicalException.class.getSimpleName() + "[", "]")).add("message='" + this.message + "'").add("cause='" + super.getCause() + "'").toString();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
