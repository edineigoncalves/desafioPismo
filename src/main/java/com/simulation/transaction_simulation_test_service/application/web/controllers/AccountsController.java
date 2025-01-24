package com.simulation.transaction_simulation_test_service.application.web.controllers;

import com.simulation.transaction_simulation_test_service.application.dtos.requests.CreateAccountDto;
import com.simulation.transaction_simulation_test_service.application.dtos.responses.GetAccountInfoResponse;
import com.simulation.transaction_simulation_test_service.domain.services.AccountsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    private final AccountsService accountsService;

    public AccountsController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody CreateAccountDto createAccountDto) {
        accountsService.createAccount(createAccountDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<GetAccountInfoResponse> getAccountInfoResponseResponse(@PathVariable Integer accountId) {
        var account = accountsService.getAccountInfo(accountId);
        return ResponseEntity.ok(account);
    }
}
