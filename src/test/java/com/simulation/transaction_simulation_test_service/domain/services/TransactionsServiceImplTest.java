package com.simulation.transaction_simulation_test_service.domain.services;

import com.simulation.transaction_simulation_test_service.domain.exceptions.AccountNotFoundException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.InvalidOperationTypeException;
import com.simulation.transaction_simulation_test_service.domain.strategies.OperationStrategy;
import com.simulation.transaction_simulation_test_service.factories.AccountsFactory;
import com.simulation.transaction_simulation_test_service.factories.RegisterTransactionRequestFactory;
import com.simulation.transaction_simulation_test_service.factories.TransactionsFactory;
import com.simulation.transaction_simulation_test_service.resources.repositories.AccountsRepository;
import com.simulation.transaction_simulation_test_service.resources.repositories.TransactionsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TransactionsServiceImplTest {

    @Autowired
    private TransactionsService transactionsService;

    @MockitoBean
    private AccountsRepository accountsRepository;

    @MockitoBean
    private TransactionsRepository transactionsRepository;

    @Autowired
    private Map<String, OperationStrategy> operationStrategies;

    @Test
    void testRegisterTransaction_whenOperationTypeIsCompraAVista_shouldBeOk() {
        var registerTransactionRequestMock = RegisterTransactionRequestFactory.sampleWithOperationId("1");
        var transactionMock = TransactionsFactory.sampleWithOperationId(1);
        var accountMock = AccountsFactory.sample();

        doReturn(Optional.of(accountMock)).when(accountsRepository).findById(any());
        doReturn(transactionMock).when(transactionsRepository).save(any());

        transactionsService.registerTransaction(registerTransactionRequestMock);

        verify(accountsRepository, times(1)).findById(any());
        verify(transactionsRepository, times(1)).save(any());
    }

    @Test
    void testRegisterTransaction_whenOperationTypeIsCompraParcelada_shouldBeOk() {
        var registerTransactionRequestMock = RegisterTransactionRequestFactory.sampleWithOperationId("2");
        var transactionMock = TransactionsFactory.sampleWithOperationId(2);
        var accountMock = AccountsFactory.sample();

        doReturn(Optional.of(accountMock)).when(accountsRepository).findById(any());
        doReturn(transactionMock).when(transactionsRepository).save(any());

        transactionsService.registerTransaction(registerTransactionRequestMock);

        verify(accountsRepository, times(1)).findById(any());
        verify(transactionsRepository, times(1)).save(any());
    }

    @Test
    void testRegisterTransaction_whenOperationTypeIsSaque_shouldBeOk() {
        var registerTransactionRequestMock = RegisterTransactionRequestFactory.sampleWithOperationId("3");
        var transactionMock = TransactionsFactory.sampleWithOperationId(3);
        var accountMock = AccountsFactory.sample();

        doReturn(Optional.of(accountMock)).when(accountsRepository).findById(any());
        doReturn(transactionMock).when(transactionsRepository).save(any());

        transactionsService.registerTransaction(registerTransactionRequestMock);

        verify(accountsRepository, times(1)).findById(any());
        verify(transactionsRepository, times(1)).save(any());
    }

    @Test
    void testRegisterTransaction_whenOperationTypeIsPagamento_shouldBeOk() {
        var registerTransactionRequestMock = RegisterTransactionRequestFactory.sampleWithOperationId("4");
        var transactionMock = TransactionsFactory.sampleWithOperationId(4);
        var accountMock = AccountsFactory.sample();

        doReturn(Optional.of(accountMock)).when(accountsRepository).findById(any());
        doReturn(transactionMock).when(transactionsRepository).save(any());

        transactionsService.registerTransaction(registerTransactionRequestMock);

        verify(accountsRepository, times(1)).findById(any());
        verify(transactionsRepository, times(1)).save(any());
    }

    @Test
    void testRegisterTransaction_whenOperationTypeIsInvalid_shouldBeAndException() {
        var registerTransactionRequestMock = RegisterTransactionRequestFactory.sampleWithOperationId("5");

        assertThrows(InvalidOperationTypeException.class, () -> {
            transactionsService.registerTransaction(registerTransactionRequestMock);
        });
    }

    @Test
    void testRegisterTransaction_whenAccountIdIsInvalid_shouldBeAnException() {
        var registerTransactionRequestMock = RegisterTransactionRequestFactory.sampleWithOperationId("4");

        doReturn(Optional.empty()).when(accountsRepository).findById(any());

        assertThrows(AccountNotFoundException.class, () -> {
            transactionsService.registerTransaction(registerTransactionRequestMock);
        });
    }
}
