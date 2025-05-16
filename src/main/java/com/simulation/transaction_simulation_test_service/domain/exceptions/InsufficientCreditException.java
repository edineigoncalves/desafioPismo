package com.simulation.transaction_simulation_test_service.domain.exceptions;

public class InsufficientCreditException extends RuntimeException {
    public InsufficientCreditException(String message) {
        super(message);
    }
}
