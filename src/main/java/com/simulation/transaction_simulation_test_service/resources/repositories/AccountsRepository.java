package com.simulation.transaction_simulation_test_service.resources.repositories;

import com.simulation.transaction_simulation_test_service.domain.entities.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
    Optional<Accounts> findByDocumentNumber(String documentNumber);
}
