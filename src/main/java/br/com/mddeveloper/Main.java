package br.com.mddeveloper;

import br.com.mddeveloper.Repository.CatalogRepository;
import br.com.mddeveloper.Repository.UserRepository;
import br.com.mddeveloper.Service.CatalogService;
import br.com.mddeveloper.Service.UserService;
import br.com.mddeveloper.Setup.DatabaseSetup;
import br.com.mddeveloper.Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        DatabaseSetup.initialize();
        Connection connection = DatabaseConnection.getConnection();

        UserRepository userRepository = new UserRepository(connection);
        UserService userService = new UserService();

        CatalogRepository catalogRepository = new CatalogRepository(connection);
        CatalogService catalogService = new CatalogService(catalogRepository);



        System.out.println("Bem vindo a Biblioteca da Plataforma Impact");

        while (true) {
            System.out.printf("\nEscolha uma opção:\n");
            System.out.printf("1 - Adicionar livro no catalago\n");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    catalogService.addBookToCatalog();
                    break;
                case 2:
                    catalogService.displayCatalog();
                    break;
                case 3:
                    catalogService.updateBookCatalog();
                    break;
                case 4:
                    catalogService.deleteBookCatalog();
                    break;
                case 5:
                    catalogService.getBooksCatalog();
                    break;
                case 7:
                    userService.addUser();
                case 6:
                    System.exit(0);
                    scanner.close();
                    break;
                default:
                    System.out.println("Opção Inválida.");
            }
        }
    }
}