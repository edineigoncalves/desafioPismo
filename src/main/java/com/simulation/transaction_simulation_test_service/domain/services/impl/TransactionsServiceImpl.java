package com.simulation.transaction_simulation_test_service.domain.services.impl;

import com.simulation.transaction_simulation_test_service.application.dtos.requests.RegisterTransactionRequest;
import com.simulation.transaction_simulation_test_service.domain.entities.enums.OperationType;
import com.simulation.transaction_simulation_test_service.domain.exceptions.AccountNotFoundException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.InvalidOperationTypeException;
import com.simulation.transaction_simulation_test_service.domain.strategies.OperationStrategy;
import com.simulation.transaction_simulation_test_service.domain.services.TransactionsService;
import com.simulation.transaction_simulation_test_service.resources.repositories.AccountsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionsServiceImpl.class);

    private final AccountsRepository accountsRepository;

    private final Map<String, OperationStrategy> operationStrategies;

    public TransactionsServiceImpl(AccountsRepository accountsRepository, Map<String, OperationStrategy> operationStrategies) {
        this.accountsRepository = accountsRepository;
        this.operationStrategies = operationStrategies;
    }

    @Override
    public void registerTransaction(RegisterTransactionRequest registerTransactionRequest) {
        var operationType = findOperationTypeById(registerTransactionRequest.operationTypeId());
        var operationCategory = determineOperationCategory(operationType);

        var operationStrategy = operationStrategies.get(operationCategory);

        if (operationStrategy == null) {
            throw new InvalidOperationTypeException("No strategy found for operation type: " + operationType.getId());
        }

        accountsRepository.findById(registerTransactionRequest.accountId()).ifPresentOrElse(accounts -> {
            operationStrategy.processOperation(accounts, registerTransactionRequest.amount(), operationType);
        }, () -> {
            logger.error("Account with id: {} not found", registerTransactionRequest.accountId());
            throw new AccountNotFoundException(String.format(
                    "Account with id: %d not found",
                    registerTransactionRequest.accountId()
            ));
        });
    }

    private String determineOperationCategory(OperationType operationType) {
        if (
                Set.of(
                        OperationType.COMPRA_A_VISTA,
                        OperationType.COMPRA_PARCELADA,
                        OperationType.SAQUE
                ).contains(operationType)
        ) {
            return "purchase";
        } else {
            return "payment";
        }
    }

    private OperationType findOperationTypeById(String operationTypeId) {
        return Set.of(OperationType.values())
                .stream()
                .filter(op -> op.getId().equals(operationTypeId))
                .findFirst()
                .orElseThrow(() -> new InvalidOperationTypeException("Invalid operation type: " + operationTypeId));
    }
}
