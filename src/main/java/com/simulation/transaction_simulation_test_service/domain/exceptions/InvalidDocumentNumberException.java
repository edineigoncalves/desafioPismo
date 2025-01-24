package com.simulation.transaction_simulation_test_service.domain.exceptions;

public class InvalidDocumentNumberException extends RuntimeException {
    public InvalidDocumentNumberException(String message) {
        super(message);
    }
}
