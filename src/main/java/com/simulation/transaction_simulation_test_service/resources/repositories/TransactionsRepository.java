package com.simulation.transaction_simulation_test_service.resources.repositories;

import com.simulation.transaction_simulation_test_service.domain.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Integer> {
}
