package com.simulation.transaction_simulation_test_service.domain.entities.enums;

public enum OperationType {
    COMPRA_A_VISTA("1"),
    COMPRA_PARCELADA("2"),
    SAQUE("3"),
    PAGAMENTO("4");

    private final String id;

    OperationType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
