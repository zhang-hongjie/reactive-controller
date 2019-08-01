//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.config;

import org.springframework.http.HttpStatus;

public interface FunctionalException {
    HttpStatus getHttpStatus();

    String getMessage();

    Throwable getCause();
}
