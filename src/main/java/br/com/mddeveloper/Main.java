package br.com.mddeveloper;

import br.com.mddeveloper.Model.Catalog;
import br.com.mddeveloper.Repository.CatalogRepository;
import br.com.mddeveloper.Repository.InventoryRepository;
import br.com.mddeveloper.Repository.LoanRepository;
import br.com.mddeveloper.Repository.UserRepository;
import br.com.mddeveloper.Service.CatalogService;
import br.com.mddeveloper.Service.InventoryService;
import br.com.mddeveloper.Service.LoanService;
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
        UserService userService = new UserService(userRepository);

        InventoryRepository inventoryRepository = new InventoryRepository(connection);
        InventoryService  inventoryService = new InventoryService(inventoryRepository);

        CatalogRepository catalogRepository = new CatalogRepository(connection);
        CatalogService catalogService = new CatalogService(catalogRepository, inventoryService);

        LoanRepository loanRepository = new LoanRepository(connection, inventoryRepository, userRepository);
        LoanService loanService = new LoanService(loanRepository, inventoryRepository, userRepository);



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
                case 6:
                    userService.addUser();
                    break;
                case 7:
                    userService.updateUser();
                    break;
                case 8:
                    userService.getUsers();
                    break;
                case 9:
                    inventoryService.getInventoryItem();
                    break;
                case 10:
                    loanService.releaseLoan();
                    break;
                case 11:
                    loanService.returnLoan();
                    break;
                case 12:
                    System.exit(0);
                    scanner.close();
                    break;
                default:
                    System.out.println("Opção Inválida.");
            }
        }
    }
}