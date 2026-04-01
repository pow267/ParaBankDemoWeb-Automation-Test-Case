package com.parasoft.parabank.web.controller.exception;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationError(@NonNull AuthenticationException ex, @NonNull WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        // Prevent IE from incorrectly caching AJAX/JSON results
        // http://www.dashbay.com/2011/05/internet-explorer-caches-ajax/
        headers.add("Expires", "-1");
        // According to https://datatracker.ietf.org/doc/html/rfc7235#section-4.1,
        // servers must include at at least one WWW-Authenticate header with 401 responses
        headers.add("WWW-Authenticate", "Basic");
        return handleExceptionInternal(ex, Objects.requireNonNull(ex).getMessageBody(), headers, HttpStatus.UNAUTHORIZED, Objects.requireNonNull(request));
    }
}
