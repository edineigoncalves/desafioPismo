package com.simulation.transaction_simulation_test_service.domain.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transactions {

    public Transactions() {
    }

    private Transactions(Builder builder) {
        this.account = builder.account;
        this.operationTypeId = builder.operationTypeId;
        this.amount = builder.amount;
        this.eventDate = builder.eventDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false, updatable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Accounts account;

    @Column(name = "operation_type_id", nullable = false)
    private Integer operationTypeId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Override
    public String toString() {
        return "Transactions{" +
                "id=" + id +
                ", account=" + account +
                ", operationTypeId=" + operationTypeId +
                ", amount=" + amount +
                ", eventDate=" + eventDate +
                '}';
    }

    public static class Builder {
        private Accounts account;
        private Integer operationTypeId;
        private Double amount;
        private LocalDateTime eventDate;

        public Builder setAccountId(Accounts account) {
            this.account = account;
            return this;
        }

        public Builder setOperationTypeId(Integer operationTypeId) {
            this.operationTypeId = operationTypeId;
            return this;
        }

        public Builder setAmount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setEventDate(LocalDateTime eventDate) {
            this.eventDate = eventDate;
            return this;
        }

        public Transactions build() {
            return new Transactions(this);
        }
    }
}
