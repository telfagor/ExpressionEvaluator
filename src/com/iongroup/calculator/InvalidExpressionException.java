package com.iongroup.calculator;

public class InvalidExpressionException extends RuntimeException {

    public InvalidExpressionException(String message) {
        super(message);
    }
}
