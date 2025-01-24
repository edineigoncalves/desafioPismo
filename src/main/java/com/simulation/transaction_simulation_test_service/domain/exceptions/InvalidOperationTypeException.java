package com.simulation.transaction_simulation_test_service.domain.exceptions;

public class InvalidOperationTypeException extends RuntimeException {
    public InvalidOperationTypeException(String message) {
        super(message);
    }
}
