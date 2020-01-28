package org.sam.melchor.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String email) {
        super(email + " is not found");
    }
}
