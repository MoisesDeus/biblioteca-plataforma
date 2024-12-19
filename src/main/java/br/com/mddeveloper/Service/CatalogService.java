package br.com.mddeveloper.Service;

import br.com.mddeveloper.Model.Catalog;
import br.com.mddeveloper.Repository.CatalogRepository;
import br.com.mddeveloper.Util.InputUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CatalogService {
    private CatalogRepository catalogRepository;
    public List<Catalog> catalogList;
    private InventoryService inventoryService;
    Scanner scanner = new Scanner(System.in);


    public CatalogService(CatalogRepository catalogRepository, InventoryService inventoryService) throws SQLException {
        this.catalogRepository = catalogRepository;
        this.catalogList = catalogRepository.getAllCatalogItems();
        this.inventoryService = inventoryService;
    }

    public void displayCatalog() {
        catalogList.forEach(System.out::println);
    }

    private Catalog getBookForm(Catalog existingBook) {
        Catalog book = existingBook != null ? existingBook : new Catalog();

        //Antiga
//        System.out.printf("Título do livro (%s):\n", book.getTitle() != null ? book.getTitle() : "Novo título");
//        String title = scanner.nextLine();
//        if (!title.isEmpty()) book.setTitle(title);

        String title = InputUtils.getStringInput("Titulo do livro", book.getTitle());
        book.setTitle(title);

        //Antiga
//        System.out.printf("Nome do autor (%s):\n", book.getAuthor() != null ? book.getAuthor() : "Novo autor");
//        String author = scanner.nextLine();
//        if (!author.isEmpty()) book.setAuthor(author);

        String author = InputUtils.getStringInput("Nome do autor(a)", book.getAuthor());
        book.setAuthor(author);

//        System.out.printf("Gênero do livro (%s):\n", book.getGenre() != null ? book.getGenre() : "Novo gênero");
//        String genre = scanner.nextLine();
//        if (!genre.isEmpty()) book.setGenre(genre);

        String genre = InputUtils.getStringInput("Gênero do livro", book.getGenre());
        book.setGenre(genre);

        System.out.printf("Ano de lançamento (%d):\n", book.getYear() != 0 ? book.getYear() : 0);
        String year = scanner.nextLine();
        if (!year.isEmpty()) book.setYear(Integer.parseInt(year));


        System.out.printf("Quantidade de páginas (%d):\n", book.getPages() != 0 ? book.getPages() : 0);
        String pages = scanner.nextLine();
        if (!pages.isEmpty()) book.setPages(Integer.parseInt(pages));

        return book;
    }

    public void addBookToCatalog() throws SQLException {

        Catalog newBook = getBookForm(null);

        System.out.println(newBook);
        System.out.printf("1 - Confirmar o cadastro do livro.\n");
        System.out.printf("2 - Cancelar o cadastro do livro.\n");
        int confirm = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Quantas cópias vão ser adicionadas ao inventário?");
        int copies = scanner.nextInt();
        scanner.nextLine();

        if (confirm == 1) {
            int generatedIdCatalog = catalogRepository.saveCatalogItem(newBook);
            newBook.setId(generatedIdCatalog);
            catalogList.add(newBook);
            System.out.println("Livro cadastrado com sucesso!");
            if (copies > 1) {
                for (int c = 0; c < copies; c++) {
                    inventoryService.addBookToInventory(generatedIdCatalog);
                }
            }
            System.out.println("Cópias cadastradas com sucesso");

        } else {
            System.out.println("Cadastro cancelado.");
        }

    }

    public void updateBookCatalog() throws SQLException {

        System.out.println("Digite o código do livro que deseja editar:");
        int updateBook = scanner.nextInt();
        scanner.nextLine();

        Catalog existingBook = catalogRepository.getCatalogItemById(updateBook);

        if (existingBook != null) {
            Catalog updatedBook = getBookForm(existingBook);
            catalogRepository.updateCatalogItem(updatedBook);
            System.out.println("Livro atualizado com sucesso!");
            System.out.println(updatedBook);
        } else {
            System.out.println("Livro não encontrado.");
        }
    }

    public void deleteBookCatalog() throws SQLException {
        System.out.println("Digite o código do item do catalago, que deseja excluir:");
        int bookToDelete = scanner.nextInt();
        scanner.nextLine();

        Catalog removeBook = catalogRepository.getCatalogItemById(bookToDelete);

        if (removeBook != null) {
            catalogRepository.deleteCatalogItem(bookToDelete);
            catalogList.remove(removeBook);
            System.out.println("Item excluído com sucesso!");
        } else {
            System.out.println("Item não encontrado no catálogo.");
        }
    }

    public void getBooksCatalog() throws SQLException {
        System.out.println("Pesquisar: ");
        System.out.println("1 - Titulo:");
        System.out.println("2 - Autor");
        System.out.println("3 - Gênero");
        int search = scanner.nextInt();
        scanner.nextLine();

        switch (search) {
            case 1:
                System.out.printf("Digite o nome do titulo:");
                String title = scanner.nextLine();

                List<Catalog> booksByTitle = catalogRepository.getCatalogItemsByTitle(title);
                if (!booksByTitle.isEmpty()) {
                    booksByTitle.forEach(System.out::println);
                } else {
                    System.out.println("Nenhum livro encontrado com o título informado");
                }
                break;
            case 2:
                System.out.printf("Digite o nome do autor:");
                String author = scanner.nextLine();

                List<Catalog> booksByAuthor = catalogRepository.getCatalogItemsByAuthor(author);
                if (!booksByAuthor.isEmpty()) {
                    booksByAuthor.forEach(System.out::println);
                } else {
                    System.out.println("Nenhum livro encontrado com o autor informado");
                }
                break;
            case 3:
                System.out.printf("Digite o nome do genero:");
                String genre = scanner.nextLine();

                List<Catalog> booksByGenre = catalogRepository.getCatalogItemsByGenre(genre);
                if (!booksByGenre.isEmpty()) {
                    booksByGenre.forEach(System.out::println);
                } else {
                    System.out.println("Nenhum livro encontrado com o gênero informado");
                }
            default:
                System.out.println("Nenhuma opção válida");
                break;
        }
    }

}
