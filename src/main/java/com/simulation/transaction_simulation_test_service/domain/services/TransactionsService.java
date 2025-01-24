package com.simulation.transaction_simulation_test_service.domain.services;

import com.simulation.transaction_simulation_test_service.application.dtos.requests.RegisterTransactionRequest;

public interface TransactionsService {
    void registerTransaction(RegisterTransactionRequest registerTransactionRequest);
}
