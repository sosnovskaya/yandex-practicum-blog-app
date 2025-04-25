package edu.misosnovskaya.exceptions;

public class CommonDBException extends RuntimeException {
    public CommonDBException(String message, Exception e) {
        super(message, e);
    }
}
