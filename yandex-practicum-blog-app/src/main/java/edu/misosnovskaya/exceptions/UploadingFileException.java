package edu.misosnovskaya.exceptions;

public class UploadingFileException extends RuntimeException {
    public UploadingFileException(String message, Exception e) {
        super(message, e);
    }
}
