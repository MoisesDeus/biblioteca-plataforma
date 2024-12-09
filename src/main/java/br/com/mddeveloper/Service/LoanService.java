package br.com.mddeveloper.Service;

import br.com.mddeveloper.Model.Inventory;
import br.com.mddeveloper.Model.Loan;
import br.com.mddeveloper.Model.User;
import br.com.mddeveloper.Repository.InventoryRepository;
import br.com.mddeveloper.Repository.LoanRepository;
import br.com.mddeveloper.Repository.UserRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoanService {
    private LoanRepository loanRepository;
    private InventoryRepository inventoryRepository;
    private UserRepository userRepository;
    public List<Loan> loanList;

    Scanner scanner = new Scanner(System.in);

    public LoanService(LoanRepository loanRepository, InventoryRepository inventoryRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.loanList = new ArrayList<>();
    }

    public void releaseLoan() throws SQLException {
        System.out.println("Digite o código do livro");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o código do usuário");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Inventory bookSelected = inventoryRepository.getInventoryItemById(bookId);
        User userSelected = userRepository.getUserById(userId);
        Inventory isAvailable = inventoryRepository.isAvailable(bookId);

        if (bookSelected != null && userSelected != null && isAvailable != null) {
            Loan loan = new Loan(bookSelected, userSelected);
            loanRepository.saveLoan(loan);
            loanList.add(loan);
            System.out.println(loan);
            System.out.printf("Empŕestimo realizado com sucesso!");
        } else if (isAvailable == null) {
            System.out.printf("Livro não está disponível");
        } else {
            System.out.printf("Livro ou Usuario não encontrado!");
        }
    }

    public void returnLoan() throws SQLException {
        System.out.println("Digite o código do usuário:");
        int userId = scanner.nextInt();
        scanner.nextLine();

        List<Loan> loansUserActives = loanRepository.getLoanList(userId);
        if (!loansUserActives.isEmpty()) {
            System.out.println("Empréstimos ativos desse usuário");
            loansUserActives.forEach(System.out::println);
        } else {
            System.out.println("Nenhum empréstimo ativo desse usuário");
        }

        System.out.println("Digite o código do livro em empréstimo que deseja retornar:");
        int bookId = scanner.nextShort();
        scanner.nextLine();

        Loan existingLoan = loanRepository.getLoan(bookId);

        if (existingLoan != null) {
            System.out.println("Digite a data de retorno do empréstimo:");
            String returnDateStr = scanner.nextLine();
            Date returnDate = Date.valueOf(returnDateStr);
            loanRepository.updateLoan(returnDate, existingLoan);
            System.out.println("Empréstimo devolvido com sucesso!");
        } else {
            System.out.println("Aconteceu algum erro. ):");
        }
    }
}
