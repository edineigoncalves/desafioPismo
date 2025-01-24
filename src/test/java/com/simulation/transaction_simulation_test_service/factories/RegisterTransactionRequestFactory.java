package com.simulation.transaction_simulation_test_service.factories;

import com.simulation.transaction_simulation_test_service.application.dtos.requests.RegisterTransactionRequest;

public class RegisterTransactionRequestFactory {
    public static RegisterTransactionRequest sampleWithOperationId(String operationId) {
        return new RegisterTransactionRequest(1, operationId, 20.50);
    }
}
