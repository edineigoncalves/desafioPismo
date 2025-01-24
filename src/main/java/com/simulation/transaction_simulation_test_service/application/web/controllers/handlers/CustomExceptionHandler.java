package com.simulation.transaction_simulation_test_service.application.web.controllers.handlers;

import com.simulation.transaction_simulation_test_service.application.web.controllers.handlers.exceptions.ApiErrorMessage;
import com.simulation.transaction_simulation_test_service.domain.exceptions.AccountAlreadyExistsException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.AccountNotFoundException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.InvalidDocumentNumberException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.InvalidOperationTypeException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleRuntimeException(RuntimeException runtimeException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, runtimeException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleAccountAlreadyExistsException(AccountAlreadyExistsException accountAlreadyExistsException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.CONFLICT, accountAlreadyExistsException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.NOT_FOUND, accountNotFoundException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleInvalidDocumentNumberException(InvalidDocumentNumberException invalidDocumentNumberException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, invalidDocumentNumberException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ApiErrorMessage> handleInvalidOperationTypeException(InvalidOperationTypeException invalidOperationTypeException) {
        var apiErrorMessage = new ApiErrorMessage(HttpStatus.BAD_REQUEST, invalidOperationTypeException.getMessage());
        return new ResponseEntity<>(apiErrorMessage, new HttpHeaders(), apiErrorMessage.httpStatus());
    }
}
