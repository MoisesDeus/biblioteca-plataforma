package br.com.mddeveloper.Model;

import java.time.LocalDate;
import java.util.Date;

public class Loan {
    private Inventory inventory;
    private User user;
    private Date loanDate;
    private Date expectedReturnDate;
    private Date ActualReturnDate;

    public Loan(Inventory inventory, User user, Date loanDate, Date expectedReturnDate, Date actualReturnDate) {
        this.inventory = inventory;
        this.user = user;
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        ActualReturnDate = actualReturnDate;
    }

    public Loan(int idInventory, int idUser, java.sql.Date loanDate, java.sql.Date expectedReturnDate, java.sql.Date actualReturnDate) {
        this.inventory = new Inventory(idInventory);
        this.user = new User(idUser);
        this.loanDate = loanDate;
        this.expectedReturnDate = expectedReturnDate;
        this.ActualReturnDate = actualReturnDate;

    }

    public Loan(Inventory bookSelected, User userSelected) {
        this.inventory = bookSelected;
        this.user = userSelected;
        this.loanDate = java.sql.Date.valueOf(LocalDate.now());
    }

    public Inventory getInventory() {
        return inventory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public java.sql.Date getLoanDate() {
        return (java.sql.Date) loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public java.sql.Date getExpectedReturnDate() {
        return (java.sql.Date) expectedReturnDate;
    }

    public void setExpectedReturnDate(Date expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public java.sql.Date getActualReturnDate() {
        return (java.sql.Date) ActualReturnDate;
    }

    public void setActualReturnDate(Date actualReturnDate) {
        ActualReturnDate = actualReturnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "inventory=" + inventory +
                ", user=" + user +
                ", loanDate=" + loanDate +
                ", expectedReturnDate=" + expectedReturnDate +
                ", ActualReturnDate=" + ActualReturnDate +
                '}';
    }
}
