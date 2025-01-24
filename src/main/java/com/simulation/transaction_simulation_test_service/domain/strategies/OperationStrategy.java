package com.simulation.transaction_simulation_test_service.domain.strategies;

import com.simulation.transaction_simulation_test_service.domain.entities.Accounts;
import com.simulation.transaction_simulation_test_service.domain.entities.enums.OperationType;

public interface OperationStrategy {
    void processOperation(Accounts account, Double amount, OperationType operationType);
}
