package br.com.mddeveloper.Util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class InputUtils {
    private static final Scanner scanner = new Scanner(System.in);

    // Método para capturar texto e converter para maiúsculas
    public static String getStringInput(String prompt, String defaultValue) {
        System.out.printf("%s (%s): ", prompt, defaultValue != null ? defaultValue : "Novo valor");
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? defaultValue : input.toLowerCase();
    }

    // Método para capturar e validar e-mail
    public static String getEmailInput(String prompt, String defaultValue) {
        while (true) {
            String email = getStringInput(prompt, defaultValue);
            if (email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                return email;
            } else {
                System.out.println("E-mail inválido. Tente novamente.");
            }
        }
    }

    // Método para capturar e validar telefone
    public static String getPhoneInput(String prompt, String defaultValue) {
        while (true) {
            System.out.printf("%s (%s): ", prompt, defaultValue != null ? defaultValue : "Novo telefone (ex: 21987654321)");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                return defaultValue;
            }

            // Remover caracteres não numéricos
            String digits = input.replaceAll("[^0-9]", "");

            // Validar se há pelo menos 10 dígitos (DD + número)
            if (digits.length() == 11) {
                String formattedPhone = String.format("(%s) %s-%s", digits.substring(0, 2), digits.substring(2, 7), digits.substring(7));
                return formattedPhone;
            } else {
                System.out.println("Telefone inválido. Insira 11 dígitos (DD + número com 9 dígitos).");
            }
        }
    }

    // Método para capturar e validar data de nascimento
    public static Date getDateInput(String prompt, Date defaultValue) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while (true) {
            System.out.printf("%s (%s): ", prompt, defaultValue != null ? defaultValue.toString() : "Nova data (AnoMêsDia)");
            String input = scanner.nextLine().trim();

            if (input.isEmpty() && defaultValue != null) {
                return defaultValue;
            }

            // Remover possíveis hífens
            String digits = input.replaceAll("[^0-9]", "");

            // Verificar se há exatamente 8 dígitos
            if (digits.length() == 8) {
                // Formatar para o padrão yyyy-MM-dd
                String formattedDate = digits.substring(0, 4) + "-" + digits.substring(4, 6) + "-" + digits.substring(6);
                try {
                    LocalDate localDate = LocalDate.parse(formattedDate, formatter);
                    return Date.valueOf(localDate);
                } catch (Exception e) {
                    System.out.println("Data inválida. Use o formato AnoMêsDia (ex: 20240101).");
                }
            } else {
                System.out.println("Data inválida. Insira exatamente 8 dígitos (AnoMêsDia).");
            }
        }
    }
}
