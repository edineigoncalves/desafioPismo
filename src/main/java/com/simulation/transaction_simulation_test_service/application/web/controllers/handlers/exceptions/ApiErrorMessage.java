package com.simulation.transaction_simulation_test_service.application.web.controllers.handlers.exceptions;

import org.springframework.http.HttpStatus;

public record ApiErrorMessage(HttpStatus httpStatus, String error) {
}
