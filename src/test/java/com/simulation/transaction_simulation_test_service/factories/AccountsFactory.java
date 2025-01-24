package com.simulation.transaction_simulation_test_service.factories;

import com.simulation.transaction_simulation_test_service.domain.entities.Accounts;

import java.util.List;

public class AccountsFactory {
    public static Accounts sample() {
        return new Accounts(1, "80321522028", List.of());
    }

    public static Accounts sampleWithCpf(String cpf) {
        return new Accounts.Builder()
                .setDocumentNumber(cpf)
                .build();
    }
}
