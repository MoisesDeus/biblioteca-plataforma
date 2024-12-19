package br.com.mddeveloper;

import br.com.mddeveloper.Controller.MenuController;
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

public class Main {
    public static void main(String[] args) throws SQLException {
        DatabaseSetup.initialize();
        Connection connection = DatabaseConnection.getConnection();

        UserRepository userRepository = new UserRepository(connection);
        UserService userService = new UserService(userRepository);

        InventoryRepository inventoryRepository = new InventoryRepository(connection);
        InventoryService  inventoryService = new InventoryService(inventoryRepository);

        CatalogRepository catalogRepository = new CatalogRepository(connection);
        CatalogService catalogService = new CatalogService(catalogRepository, inventoryService);

        LoanRepository loanRepository = new LoanRepository(connection, inventoryRepository, userRepository);
        LoanService loanService = new LoanService(loanRepository, inventoryRepository, userRepository, inventoryService);

        MenuController menuController = new MenuController(catalogService, inventoryService, userService, loanService);
        menuController.showMainMenu();
    }
}