package com.simulation.transaction_simulation_test_service.application.dtos.requests;

public record RegisterTransactionRequest(
        Integer accountId,
        String operationTypeId,
        Double amount
) {
}
