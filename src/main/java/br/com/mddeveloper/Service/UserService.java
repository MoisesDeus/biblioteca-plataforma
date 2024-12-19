package br.com.mddeveloper.Service;

import br.com.mddeveloper.Model.User;
import br.com.mddeveloper.Repository.UserRepository;
import br.com.mddeveloper.Util.DateUtils;
import br.com.mddeveloper.Util.InputUtils;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private UserRepository userRepository;
    public List<User> userList;
    Scanner scanner  = new Scanner(System.in);

    public UserService(UserRepository userRepository) throws SQLException {
        this.userRepository = userRepository;
        this.userList = userRepository.getAllUsers();
    }

    public void displayUsers() {
        userList.forEach(System.out::println);
    }

    private User getUserForm(User existingUser) {
        User user = existingUser != null ? existingUser : new User();

        //Antiga
//        System.out.printf("Nome (%s):\n", user.getName() != null ? user.getName() : "Novo nome:");
//        String name = scanner.nextLine();
//        if (!name.isEmpty()) user.setName(name);

        String name = InputUtils.getStringInput("Nome", user.getName());
        user.setName(name);

        //Antiga
//        System.out.printf("Email (%s):\n", user.getEmail() != null ? user.getEmail() : "Novo email");
//        String email = scanner.nextLine();
//        if (!email.isEmpty()) user.setEmail(email);

        String email = InputUtils.getEmailInput("Email", user.getEmail());
        user.setEmail(email);

        //Antiga
//        System.out.printf("Endereço (%s):\n", user.getAddress() != null ? user.getAddress() : "Novo Endereço");
//        String address = scanner.nextLine();
//        if (!address.isEmpty()) user.setAddress(address);

        String address = InputUtils.getStringInput("Endereço", user.getAddress());
        user.setAddress(address);

        //Antiga
//        System.out.printf("telefone (%s):\n", user.getPhone() != null ? user.getPhone() : "Novo telefone");
//        String phone = scanner.nextLine();
//        if (!phone.isEmpty()) user.setPhone(phone);

        String phone = InputUtils.getPhoneInput("Telefone", user.getPhone());
        user.setPhone(phone);

        //Antiga
//        Date birthDate;
//        System.out.printf("data de Nascimento (Ano-Mês-Dia):\n", user.getBirthDate() != null ? user.getBirthDate() : "Nova data de nascimento (Ano-Mês-Dia)");
//        String birthDateStr = scanner.nextLine();
//        birthDate = Date.valueOf(birthDateStr);
//        System.out.println(birthDateStr);
//        if (!(birthDate == null)) {
//            user.setBirthDate(Date.valueOf(DateUtils.toLocalDate(birthDate)));
//        }

        Date birthDate = InputUtils.getDateInput("Data de nascimento (ANO-MÊS-DIA)", user.getBirthDate());
        user.setBirthDate(birthDate);

        return user;
    }

    public void addUser() throws SQLException {
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

    public void updateUser() throws SQLException {
        System.out.println("Digite o código do usuário que deseja editar:");
        int updateUser = scanner.nextInt();
        scanner.nextLine();

        User existingUser = null;
        for (User u : userList) {
            if (u.getId() == updateUser) {
                existingUser = u;
                break;
            }
        }

        if (existingUser != null) {
            User updatedUser = getUserForm(existingUser);
            userRepository.updateUserRepository(updatedUser);
            System.out.println("Usuário atualizado com sucesso!");
            System.out.println(updatedUser);
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    public void getUsers() {
        System.out.println("Carregando usuários...");
        displayUsers();
    }
}
