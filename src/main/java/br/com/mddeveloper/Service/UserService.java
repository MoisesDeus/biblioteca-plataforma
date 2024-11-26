package br.com.mddeveloper.Service;

import br.com.mddeveloper.Model.User;
import br.com.mddeveloper.Repository.UserRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private UserRepository userRepository;
    private List<User> userList;
    Scanner scanner  = new Scanner(System.in);

    public void displayUsers() {
        userList.forEach(System.out::println);
    }

    private User getUserForm(User existingUser) {
        User user = existingUser != null ? existingUser : new User();

        System.out.printf("Nome (%s):\n", user.getName() != null ? user.getName() : "Novo nome:");
        String name = scanner.nextLine();
        if (!name.isEmpty()) user.setName(name);

        System.out.printf("Email (%s):\n", user.getEmail() != null ? user.getEmail() : "Novo email");
        String email = scanner.nextLine();
        if (!email.isEmpty()) user.setEmail(email);

        System.out.printf("Endereço (%s):\n", user.getAddress() != null ? user.getAddress() : "Novo Endereço");
        String address = scanner.nextLine();
        if (!address.isEmpty()) user.setAddress(address);

        System.out.printf("telefone (%s):\n", user.getPhone() != null ? user.getPhone() : "Novo telefone");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) user.setPhone(phone);

        System.out.printf("data de Nascimento (%s):\n", user.getBirthDate() != null ? user.getBirthDate() : "Nova data de nascimento");
        String birthDate = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (!birthDate.isEmpty()) user.getBirthDate(LocalDate.parse(birthDate, formatter));

        return user;
    }

    public void addUser() throws SQLException {
        while (true) {
            User newUser = getUserForm(null);

            System.out.println(newUser);
            System.out.printf("1 - Confirmar o cadastro de usuario.\n");
            System.out.printf("2 - Cancelar o cadastro de usuario.\n");
            int confirm = scanner.nextInt();
            scanner.nextLine();

            if (confirm == 1) {
                int generatedId = userRepository.saveUser(newUser);
                newUser.setId(generatedId);
                userList.add(newUser);
                System.out.println("Usuário cadastrado com sucesso!");
            } else {
                System.out.println("Cadastro cancelado.");
            }
        }
    }
}
