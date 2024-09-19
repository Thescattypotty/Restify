package org.restify.fileStorage_service.ExceptionHandler;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.restify.fileStorage_service.Exception.FileFailedException;
import org.restify.fileStorage_service.Payload.Response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handle(FileNotFoundException exception){
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.getMessage());
    }

    @ExceptionHandler(FileFailedException.class)
    public ResponseEntity<String> handle(FileFailedException exception){
        return ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Map<String, String> errors = new HashMap<String, String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
