package com.simulation.transaction_simulation_test_service.domain.entities;

import jakarta.persistence.*;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.List;

@Entity
@Table(name = "accounts")
public class Accounts {

    @PersistenceCreator
    public Accounts() {
    }

    public Accounts(Integer id, String documentNumber, Double availableCreditLimit, List<Transactions> transactions) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.availableCreditLimit = availableCreditLimit;
        this.transactions = transactions;
    }

    private Accounts(Builder builder) {
        this.documentNumber = builder.documentNumber;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false, updatable = false)
    private Integer id;

    @Column(
            name = "document_number",
            unique = true,
            nullable = false,
            length = 11,
            updatable = false
    )
    private String documentNumber;
    @Column(name = "available_credit_limit", nullable = false)
    private Double availableCreditLimit;

    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Transactions> transactions;

    public Integer getId() {
        return id;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public Double getAvailableCreditLimit() {
        return availableCreditLimit;
    }

    public void setAvailableCreditLimit(Double availableCreditLimit) {
        this.availableCreditLimit = availableCreditLimit;
    }

    public static class Builder {
        private String documentNumber;

        public Builder setDocumentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
            return this;
        }

        public Accounts build() {
            return new Accounts(this);
        }
    }
}
