package br.com.mddeveloper.Service;

import br.com.mddeveloper.Model.Inventory;
import br.com.mddeveloper.Repository.InventoryRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class InventoryService {
    private InventoryRepository inventoryRepository;
    public List<Inventory> inventoryList;
    Scanner scanner = new Scanner(System.in);

    public InventoryService(InventoryRepository inventoryRepository) throws SQLException {
        this.inventoryRepository = inventoryRepository;
        this.inventoryList = inventoryRepository.getAllInventory();
    }

    public void addBookToInventory(int catalogId) throws SQLException {
        Inventory inventoryBook = new Inventory();
        inventoryBook.setIdCatalog(catalogId);
        inventoryBook.setStatus("Available");
        int generatedId = inventoryRepository.saveInventoryItem(inventoryBook);
        inventoryBook.setId(generatedId);

        System.out.println("Item adicionado ao inventário com sucesso");
    }

    public void getInventoryItem() throws SQLException {
        System.out.println("Pesquisar: ");
        System.out.println("1 - Status (Emprestado ou disponível)");
        System.out.println("2 - Código do catalogo");
        System.out.println("3 - Código do inventário");
        int search = scanner.nextInt();
        scanner.nextLine();

        switch (search) {
            case 1:
                System.out.println("Pesquisar por:");
                System.out.println("1 - Disponível");
                System.out.println("2 - Emprestado");
                int isAvailable = scanner.nextInt();
                scanner.nextLine();

                if (isAvailable == 1) {
                    List<Inventory> availableList = inventoryRepository.getInventoryItemsByStatus("Available");
                    availableList.forEach(System.out::println);
                } else if (isAvailable == 2) {
                    List<Inventory> borrowedList =  inventoryRepository.getInventoryItemsByStatus("Borrowed");
                    borrowedList.forEach(System.out::println);
                }
                break;
            case 2:
                System.out.println("Digite o código do catálogo:");
                int idCatalog = scanner.nextInt();
                scanner.nextLine();

                List<Inventory> idCatalogList = inventoryRepository.getInventoryItemsByCatalogId(idCatalog);
                idCatalogList.forEach(System.out::println);

                break;
            case 3:
                System.out.println("Digite o código do Inventário:");
                int idInventory = scanner.nextInt();
                scanner.nextLine();

                inventoryRepository.getInventoryItemById(idInventory);
                break;
            default:
                System.out.println("Digite uma opção válida");
                break;
        }
    }
}
