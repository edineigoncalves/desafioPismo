package com.simulation.transaction_simulation_test_service.domain.services;

import com.simulation.transaction_simulation_test_service.domain.exceptions.AccountAlreadyExistsException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.AccountNotFoundException;
import com.simulation.transaction_simulation_test_service.domain.exceptions.InvalidDocumentNumberException;
import com.simulation.transaction_simulation_test_service.factories.AccountsFactory;
import com.simulation.transaction_simulation_test_service.factories.CreateAccountDtoFactory;
import com.simulation.transaction_simulation_test_service.resources.repositories.AccountsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountsServiceImplTest {

    @Autowired
    private AccountsService accountsService;

    @MockitoBean
    private AccountsRepository accountsRepository;

    @Test
    void testCreateAccount_whenHasAValidData_shouldBeCreate() {
        var createAccountDtoMock = CreateAccountDtoFactory.sample();
        var expectedAccountMock = AccountsFactory.sampleWithCpf(createAccountDtoMock.documentNumber());

        doReturn(Optional.empty()).when(accountsRepository).findByDocumentNumber(any());
        doReturn(expectedAccountMock).when(accountsRepository).save(any());

        accountsService.createAccount(createAccountDtoMock);

        verify(accountsRepository, times(1)).findByDocumentNumber(any());
        verify(accountsRepository, times(1)).save(any());
    }

    @Test
    void testCreateAccount_whenHasAnInvalidData_shouldNotBeCreate() {
        var createAccountDtoMock = CreateAccountDtoFactory.sample();

        doReturn(Optional.of(AccountsFactory.sample())).when(accountsRepository).findByDocumentNumber(any());

        assertThrows(AccountAlreadyExistsException.class, () -> {
            accountsService.createAccount(createAccountDtoMock);
        });

        verify(accountsRepository, times(1)).findByDocumentNumber(any());
        verify(accountsRepository, never()).save(any());
    }

    @Test
    void testCreateAccount_whenHasAInvalidCpf_shouldBeAnException() {
        var createAccountDtoMock = CreateAccountDtoFactory.sampleWithCpf("12345");

        doReturn(Optional.empty()).when(accountsRepository).findByDocumentNumber(any());

        assertThrows(InvalidDocumentNumberException.class, () -> {
            accountsService.createAccount(createAccountDtoMock);
        });

        verify(accountsRepository, times(1)).findByDocumentNumber(any());
        verify(accountsRepository, never()).save(any());
    }

    @Test
    void getAccountInfo_whenHasAValidId_shouldReturnAccountInfo() {
        var expectedAccountMock = AccountsFactory.sample();

        doReturn(Optional.of(expectedAccountMock)).when(accountsRepository).findById(any());

        var returnedAccount = accountsService.getAccountInfo(1);

        assertEquals(returnedAccount.documentNumber(), expectedAccountMock.getDocumentNumber());

        verify(accountsRepository, times(1)).findById(any());
    }

    @Test
    void getAccountInfo_whenHasAnInvalidId_shouldThrowException() {
        doReturn(Optional.empty()).when(accountsRepository).findById(any());

        assertThrows(AccountNotFoundException.class, () -> {
            accountsService.getAccountInfo(1);
        });
    }
}
