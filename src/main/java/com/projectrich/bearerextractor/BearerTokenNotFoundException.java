package com.projectrich.bearerextractor;

public class BearerTokenNotFoundException extends RuntimeException {

    private static final String MESSAGE = "Could not find any Bearer Token from Authorization Header";

    public BearerTokenNotFoundException() {
        super(MESSAGE);
    }
}
