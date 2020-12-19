package com.ugachev.exception_handling;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomerIncorrectData> handleException(CustomerNotFoundException exception) {
        CustomerIncorrectData incorrectData = new CustomerIncorrectData();
        incorrectData.setMessage(exception.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<CustomerIncorrectData> handleException(Exception exception) {
        CustomerIncorrectData incorrectData = new CustomerIncorrectData();
        incorrectData.setMessage(exception.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CustomerIncorrectData> handleException(NotFoundFieldException exception) {
        CustomerIncorrectData incorrectData = new CustomerIncorrectData();
        incorrectData.setMessage(exception.getMessage());

        return new ResponseEntity<>(incorrectData, HttpStatus.BAD_REQUEST);
    }
}
