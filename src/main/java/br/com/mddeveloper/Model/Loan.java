package br.com.mddeveloper.Model;

import java.time.LocalDate;
import java.util.Date;

public class Loan {
    private Inventory inventory;
    private User user;
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;

    public Loan(Inventory inventory, User user, LocalDate loanDate, LocalDate expectedReturnDate, LocalDate actualReturnDate) {
        this.inventory = inventory;
        this.user = user;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
    }

    public Loan(int idInventory, int idUser, LocalDate loanDate, LocalDate expectedReturnDate, LocalDate actualReturnDate) {
        this.inventory = new Inventory(idInventory);
        this.user = new User(idUser);
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;

    }

    public Loan(Inventory bookSelected, User userSelected) {
        this.inventory = bookSelected;
        this.user = userSelected;
        this.loanDate = LocalDate.now();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "inventory=" + inventory +
                ", user=" + user +
                ", loanDate=" + loanDate +
                ", expectedReturnDate=" + expectedReturnDate +
                ", ActualReturnDate=" + actualReturnDate +
                '}';
    }
}
