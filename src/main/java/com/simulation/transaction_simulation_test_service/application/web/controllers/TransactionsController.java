package com.simulation.transaction_simulation_test_service.application.web.controllers;

import com.simulation.transaction_simulation_test_service.application.dtos.requests.RegisterTransactionRequest;
import com.simulation.transaction_simulation_test_service.domain.services.TransactionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping
    public ResponseEntity<Void> registerTransaction(@RequestBody RegisterTransactionRequest registerTransactionRequest) {
        transactionsService.registerTransaction(registerTransactionRequest);
        return ResponseEntity.ok().build();
    }
}
