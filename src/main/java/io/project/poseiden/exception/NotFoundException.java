package io.project.poseiden.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends AbstractDomainException {

    public NotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
