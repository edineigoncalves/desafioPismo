package com.simulation.transaction_simulation_test_service.domain.services.impl;

import com.simulation.transaction_simulation_test_service.application.dtos.requests.CreateAccountDto;
import com.simulation.transaction_simulation_test_service.application.dtos.responses.GetAccountInfoResponse;
import com.simulation.transaction_simulation_test_service.domain.entities.Accounts;
import com.simulation.transaction_simulation_test_service.domain.exceptions.AccountAlreadyExistsException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.AccountNotFoundException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.InvalidDocumentNumberException;
import com.simulation.transaction_simulation_test_service.domain.services.AccountsService;
import com.simulation.transaction_simulation_test_service.domain.utils.CpfValidator;
import com.simulation.transaction_simulation_test_service.resources.repositories.AccountsRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AccountsServiceImpl implements AccountsService {

    private static final Logger logger = LoggerFactory.getLogger(AccountsServiceImpl.class);

    private final AccountsRepository accountsRepository;

    public AccountsServiceImpl(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void createAccount(CreateAccountDto createAccountDto) {
        logger.info("Attempting to create account with document number: {}", createAccountDto.documentNumber());

        accountsRepository.findByDocumentNumber(createAccountDto.documentNumber()).ifPresentOrElse(account -> {
            logger.error("Account with document number {} already exists", createAccountDto.documentNumber());
            throw new AccountAlreadyExistsException("Account with document number " + createAccountDto.documentNumber() + " already exists");
        }, () -> {
            validateDocumentNumber(createAccountDto.documentNumber());

            logger.info("Creating account with document number: {}", createAccountDto.documentNumber());

            var newAccount = new Accounts.Builder()
                    .setDocumentNumber(createAccountDto.documentNumber())
                    .build();

            accountsRepository.save(newAccount);

            logger.info("Account created with document number: {}", createAccountDto.documentNumber());
        });
    }

    @Override
    public GetAccountInfoResponse getAccountInfo(Integer id) {
        logger.info("Fetching account info for id: {}", id);

        var account = accountsRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Account with id: {} not found", id);
                    return new AccountNotFoundException("Account with ID " + id + " not found");
                });

        logger.info("Account found with id: {}", account.getId());

        return new GetAccountInfoResponse.Builder()
                .setAccountId(account.getId())
                .setDocumentNumber(account.getDocumentNumber())
                .build();
    }

    private static void validateDocumentNumber(String documentNumber) {
        logger.info("Validating document number: {}", documentNumber);
        if (!CpfValidator.isValidCPF(documentNumber)) {
            throw new InvalidDocumentNumberException("Invalid document number: " + documentNumber);
        }
    }
}
