package com.simulation.transaction_simulation_test_service.factories;

import com.simulation.transaction_simulation_test_service.application.dtos.requests.CreateAccountDto;

public class CreateAccountDtoFactory {
    public static CreateAccountDto sample() {
        return new CreateAccountDto("32408202000");
    }

    public static CreateAccountDto sampleWithCpf(String cpf) {
        return new CreateAccountDto(cpf);
    }
}
