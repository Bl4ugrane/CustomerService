package com.ugachev.exception_handling;

public class NotFoundFieldException extends RuntimeException{
    public NotFoundFieldException(String message) {
        super(message);
    }
}
