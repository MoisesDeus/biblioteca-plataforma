package br.com.mddeveloper.Service;

import br.com.mddeveloper.Model.Inventory;
import br.com.mddeveloper.Model.Loan;
import br.com.mddeveloper.Model.User;
import br.com.mddeveloper.Repository.LoanRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoanService {
    private LoanRepository loanRepository;
    public List<Loan> loanList;

    Scanner scanner = new Scanner(System.in);

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
        this.loanList = loanList;
    }

    public void releaseLoan() throws SQLException {
        System.out.println("Digite o código do livro");
        int bookId = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Digite o código do usuário");
        int userId = scanner.nextInt();
        scanner.nextLine();

        Inventory bookSelected = null;
        User userSelected = null;

        for (Inventory b : inventoryList) {
            if (b.getId() == bookId) {
                bookSelected = b;
                break;
            }
        }

        for (User u : userList) {
            if (u.getId() == userId)
                userSelected = u;
            break;
        }

        if (bookSelected != null && userSelected != null) {
            Loan loan = new Loan(bookSelected, userSelected);
            loanRepository.saveLoan(loan);
            loanList.add(loan);
            System.out.println(loan);
            System.out.printf("Empŕestimo realizado com sucesso!");
        } else {
            System.out.printf("Livro ou Usuario não encontrado!");
        }
    }

    public void returnLoan() throws SQLException {
        System.out.println("Digite o código do usuário:");
        int userId = scanner.nextInt();
        scanner.nextLine();

//        Loan existingLoan = null;
//        for (Loan l : loanList) {
//            if (l.getUser().getId() == userId) {
//                existingLoan = l;
//                break;
//            }
//        }

        List<Loan> loansUserActives = loanRepository.getLoan(userId);
        if (!loansUserActives.isEmpty()) {
            System.out.println("Empréstimos ativos desse usuário");
            loansUserActives.forEach(System.out::println);
        } else {
            System.out.println("Nenhum empréstimo ativo desse usuário");
        }

        System.out.println("Digite o código do livro em empréstimo que deseja retornar:");
        int bookId = scanner.nextShort();
        scanner.nextLine();

        Loan existingLoan = null;
        for (Loan l : loanList) {
            if (l.getInventory().getId() == bookId) {
                existingLoan = l;
                break;
            }
        }

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
