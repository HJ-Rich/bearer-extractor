package com.projectrich.bearerextractor;

public class BearerTokenMalformedException extends RuntimeException {

    private static final String MESSAGE = "Bearer Token is malformed";

    public BearerTokenMalformedException() {
        super(MESSAGE);
    }
}
