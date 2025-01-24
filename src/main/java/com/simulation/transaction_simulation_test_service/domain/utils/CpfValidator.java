package com.simulation.transaction_simulation_test_service.domain.utils;

public class CpfValidator {
    public static boolean isValidCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        int sum = 0;

        int[] weights = {10, 9, 8, 7, 6, 5, 4, 3, 2};

        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weights[i];
        }

        int firstDigit = 11 - (sum % 11);

        if (firstDigit == 10 || firstDigit == 11) {
            firstDigit = 0;
        }

        if (Character.getNumericValue(cpf.charAt(9)) != firstDigit) {
            return false;
        }

        sum = 0;

        int[] weights2 = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weights2[i];
        }

        int secondDigit = 11 - (sum % 11);

        if (secondDigit == 10 || secondDigit == 11) {
            secondDigit = 0;
        }

        return Character.getNumericValue(cpf.charAt(10)) == secondDigit;
    }
}