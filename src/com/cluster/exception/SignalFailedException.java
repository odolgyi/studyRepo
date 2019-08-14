package com.cluster.exception;

public class SignalFailedException extends RuntimeException {
    public SignalFailedException(String message){
        super(message);
    }

    public  SignalFailedException(String message, Throwable cause){
        super(message, cause);
    }
}
