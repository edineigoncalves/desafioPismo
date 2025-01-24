package com.simulation.transaction_simulation_test_service.factories;

import com.simulation.transaction_simulation_test_service.domain.entities.Transactions;

import java.time.LocalDateTime;

public class TransactionsFactory {
    public static Transactions sampleWithOperationId(Integer operationId) {
        return new Transactions.Builder()
                .setAccountId(AccountsFactory.sample())
                .setOperationTypeId(operationId)
                .setAmount(20.50)
                .setEventDate(LocalDateTime.now())
                .build();
    }
}
