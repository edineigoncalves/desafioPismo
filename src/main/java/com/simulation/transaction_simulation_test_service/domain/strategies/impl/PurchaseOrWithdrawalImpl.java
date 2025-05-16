package com.simulation.transaction_simulation_test_service.domain.strategies.impl;

import com.simulation.transaction_simulation_test_service.domain.entities.Accounts;
import com.simulation.transaction_simulation_test_service.domain.entities.Transactions;
import com.simulation.transaction_simulation_test_service.domain.entities.enums.OperationType;
import com.simulation.transaction_simulation_test_service.domain.exceptions.InsufficientCreditException;
import com.simulation.transaction_simulation_test_service.domain.strategies.OperationStrategy;
import com.simulation.transaction_simulation_test_service.resources.repositories.AccountsRepository;
import com.simulation.transaction_simulation_test_service.resources.repositories.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component("purchase")
public class PurchaseOrWithdrawalImpl implements OperationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrWithdrawalImpl.class);

    private final TransactionsRepository transactionsRepository;

    private final AccountsRepository accountsRepository;

    public PurchaseOrWithdrawalImpl(TransactionsRepository transactionsRepository,
                                    AccountsRepository accountsRepository) {
        this.transactionsRepository = transactionsRepository;
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void processOperation(Accounts account, Double amount, OperationType operationType) {
        logger.info("Initiate a buy in cash operation to account: {}", account.getId());

        double availableCredit = account.getAvailableCreditLimit();
        double transactionAmount = Math.abs(amount);

        if(availableCredit< transactionAmount){
            logger.warn("Insuficient limit for transaction");
            throw new InsufficientCreditException("Insufficient limite for transaction");
        }

        account.setAvailableCreditLimit(availableCredit-transactionAmount);
        accountsRepository.save(account);

        var newTransaction = new Transactions.Builder()
                .setAccountId(account)
                .setOperationTypeId(Integer.valueOf(operationType.getId()))
                .setAmount(-Math.abs(amount))
                .setEventDate(LocalDateTime.now())
                .build();

        logger.info("New transaction created: {}", newTransaction);
        transactionsRepository.save(newTransaction);
    }
}
