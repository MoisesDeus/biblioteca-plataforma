package br.com.mddeveloper.Service;

import br.com.mddeveloper.Model.Catalog;
import br.com.mddeveloper.Repository.CatalogRepository;

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

        System.out.printf("Título do livro (%s):\n", book.getTitle() != null ? book.getTitle() : "Novo título");
        String title = scanner.nextLine();
        if (!title.isEmpty()) book.setTitle(title);

        System.out.printf("Nome do autor (%s):\n", book.getAuthor() != null ? book.getAuthor() : "Novo autor");
        String author = scanner.nextLine();
        if (!author.isEmpty()) book.setAuthor(author);

        System.out.printf("Gênero do livro (%s):\n", book.getGenre() != null ? book.getGenre() : "Novo gênero");
        String genre = scanner.nextLine();
        if (!genre.isEmpty()) book.setGenre(genre);

        System.out.printf("Ano de lançamento (%d):\n", book.getYear() != 0 ? book.getYear() : 0);
        String year = scanner.nextLine();
        if (!year.isEmpty()) book.setYear(Integer.parseInt(year));

        System.out.printf("Quantidade de páginas (%d):\n", book.getPages() != 0 ? book.getPages() : 0);
        String pages = scanner.nextLine();
        if (!pages.isEmpty()) book.setPages(Integer.parseInt(pages));

        return book;
    }

    public void addBookToCatalog() throws SQLException {
        while (true) {
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
                    break;
                }
                System.out.println("Cópias cadastradas com sucesso");

            } else {
                System.out.println("Cadastro cancelado.");
            }

            System.out.printf("1 - Cadastrar novo livro\n");
            System.out.printf("2 - Editar livro cadastrado\n");
            System.out.println("3 - Excluir livro do catalogo");
            System.out.printf("4 - Sair\n");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                addBookToCatalog();
            } else if (option == 2) {
                updateBookCatalog();
            } else if (option == 3) {
                deleteBookCatalog();
            } else {
                System.exit(0);
            }
        }
    }

    public void updateBookCatalog() throws SQLException {
        while (true) {
            System.out.println("Digite o código do livro que deseja editar:");
            int updateBook = scanner.nextInt();
            scanner.nextLine();

            Catalog existingBook = null;
            for (Catalog b : catalogList) {
                if (b.getId() == updateBook) {
                    existingBook = b;
                    break;
                }
            }

            if (existingBook != null) {
                Catalog updatedBook = getBookForm(existingBook);
                catalogRepository.updateCatalogItem(updatedBook);
                System.out.println("Livro atualizado com sucesso!");
                System.out.println(updatedBook);
            } else {
                System.out.println("Livro não encontrado.");
            }

            System.out.println("1 - Cadastrar novo livro\n");
            System.out.println("2 - Editar livro cadastrado\n");
            System.out.println("3 - Excluir livro do catalogo");
            System.out.println("3 - Sair\n");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                addBookToCatalog();
            } else if (option == 2) {
                updateBookCatalog();
            } else if (option == 3) {
                deleteBookCatalog();
            } else {
                System.exit(0);
            }
        }

    }

    public void deleteBookCatalog() throws SQLException {
        System.out.println("Digite o código do item do catalago, que deseja excluir:");
        int bookToDelete = scanner.nextInt();
        scanner.nextLine();

        Catalog removeBook = null;

        for (Catalog b : catalogList) {
            if (b.getId() == bookToDelete) {
                removeBook = b;
                break;
            }
        }

        if (removeBook != null) {
            catalogRepository.deleteCatalogItem(bookToDelete);
            catalogList.remove(removeBook);
            System.out.println("Item excluído com sucesso!");
        } else {
            System.out.println("Item não encontrado no catálogo.");
        }

        System.out.println("1 - Cadastrar novo livro\n");
        System.out.println("2 - Editar livro cadastrado\n");
        System.out.println("3 - Excluir livro do catalogo");
        System.out.println("3 - Sair\n");
        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) {
            addBookToCatalog();
        } else if (option == 2) {
            updateBookCatalog();
        } else if (option == 3) {
            deleteBookCatalog();
        } else {
            System.exit(0);
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

//                for (Catalog b : catalogList) {
//                    if (b.getTitle().equals(title)) {
//                        //catalogRepository.getCatalogItemsByTitle(title);
//                        System.out.println("Encontrado: \n" + catalogRepository.getCatalogItemsByTitle(title));
//                    } else {
//                        System.out.println("Livro não encontrado no catalogo");
//                    }
//                }
//                break;
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
