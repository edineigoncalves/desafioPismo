package com.simulation.transaction_simulation_test_service.domain.exceptions;

public class AccountAlreadyExistsException extends RuntimeException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
