package com.simulation.transaction_simulation_test_service.application.dtos.responses;

public record GetAccountInfoResponse(Integer accountId, String documentNumber) {

    public static class Builder {
        private Integer accountId;
        private String documentNumber;

        public Builder setAccountId(Integer accountId) {
            this.accountId = accountId;
            return this;
        }

        public Builder setDocumentNumber(String documentNumber) {
            this.documentNumber = documentNumber;
            return this;
        }

        public GetAccountInfoResponse build() {
            return new GetAccountInfoResponse(accountId, documentNumber);
        }
    }
}
