package edu.misosnovskaya.advice;

import edu.misosnovskaya.exceptions.ImageNotFoundException;
import edu.misosnovskaya.exceptions.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NotFoundExceptionAdvice {

    @ExceptionHandler({PostNotFoundException.class, ImageNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Resource is not found!");
    }
}