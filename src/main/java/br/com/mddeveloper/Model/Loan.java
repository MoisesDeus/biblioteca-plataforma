package br.com.mddeveloper.Model;

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

    public Loan(int idInventory, int idUser, java.sql.Date loanDate, java.sql.Date expectedReturn, java.sql.Date actualReturnDate) {
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
}
