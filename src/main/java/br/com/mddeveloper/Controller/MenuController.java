package br.com.mddeveloper.Controller;

import br.com.mddeveloper.Service.CatalogService;
import br.com.mddeveloper.Service.InventoryService;
import br.com.mddeveloper.Service.LoanService;
import br.com.mddeveloper.Service.UserService;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuController {
    private final CatalogService catalogService;
    private final InventoryService inventoryService;
    private final UserService userService;
    private final LoanService loanService;
    Scanner scanner = new Scanner(System.in);

    public MenuController(CatalogService catalogService, InventoryService inventoryService, UserService userService, LoanService loanService) {
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
        this.userService = userService;
        this.loanService = loanService;
    }

    public void showMainMenu() throws SQLException {
        boolean running = true;
        while (running) {
            System.out.println("\n#### Bem vindo a Biblioteca da Plataforma Impact ####");
            System.out.println("1 - Gerenciar Usuários");
            System.out.println("2 - Gerenciar Catálogo");
            System.out.println("3 - Gerenciar Inventário");
            System.out.println("4 - Gerenciar Empréstimo");
            System.out.println("5 - Sair");
            System.out.println("Escolha uma opção: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    showUserMenu();
                    break;
                case 2:
                    showCatalogMenu();
                    break;
                case 3:
                    showInventoryMenu();
                    break;
                case 4:
                    showLoanMenu();
                    break;
                case 5:
                    System.out.println("Saindo do sistema...");
                    running = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void showUserMenu() throws SQLException {
        System.out.println("\n### Gerenciar Usuários ###");
        System.out.println("1 - Criar Usuário");
        System.out.println("2 - Editar Usuário");
        System.out.println("3 - Consultar Usuários");
        System.out.println("4 - Listar Usuários");
        System.out.println("5 - Voltar ao Menu Principal");
        System.out.println("Escolha uma opção: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                userService.addUser();
                break;
            case 2:
                userService.updateUser();
                break;
            case 3:
                userService.getUsers();
                break;
            case 4:
                userService.displayUsers();
            case 5:
                showMainMenu();
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    private void showCatalogMenu() throws SQLException {
        System.out.println("\n### Gerenciar Catálogo ###");
        System.out.println("1 - Criar Item de Catálogo");
        System.out.println("2 - Alterar Item de Catálogo");
        System.out.println("3 - Excluir Item de Catálogo");
        System.out.println("4 - Listar Catálogo");
        System.out.println("5 - Buscar Item no Catálogo");
        System.out.println("6 - Voltar ao menu principal");
        System.out.println("Escolha uma opção");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                catalogService.addBookToCatalog();
                break;
            case 2:
                catalogService.updateBookCatalog();
                break;
            case 3:
                catalogService.deleteBookCatalog();
                break;
            case 4:
                catalogService.displayCatalog();
                break;
            case 5:
                catalogService.getBooksCatalog();
                break;
            case 6:
                showMainMenu();
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    private void showInventoryMenu() throws SQLException {
        System.out.println("\n### Gerenciar Inventário ###");
        System.out.println("1 - Listar livros");
        System.out.println("2 - Voltar ao menu principal");
        System.out.println("Escolha uma opção: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                inventoryService.getInventoryItem();
                break;
            case 2:
                showMainMenu();
                break;
            default:
                System.out.println("Opção inválida");
        }
    }

    private void showLoanMenu() throws SQLException {
        System.out.println("\n### Gerenciar Empréstimos ###");
        System.out.println("1 - Emprestar livro");
        System.out.println("2 - Devolver livro");
        System.out.println("3 - Consultar empréstimos ativos");
        System.out.println("4 - Voltar ao menu principal");
        System.out.println("Escolha uma opção: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                loanService.releaseLoan();
                break;
            case 2:
                loanService.returnLoan();
                break;
            case 3:
                loanService.loanActives();
                break;
            case 4:
                showMainMenu();
                break;
            default:
                System.out.println("Opção inválida");
        }
    }
}
