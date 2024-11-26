package br.com.mddeveloper.Repository;

import br.com.mddeveloper.Model.Loan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {
    private Connection connection;

    InventoryRepository inventoryRepository = new InventoryRepository(connection);

    public LoanRepository(Connection connection) {
        this.connection = connection;
    }

    public Loan saveLoan(Loan loan) throws SQLException {
        String sql = "INSERT INTO Loans (ID_Inventory, ID_User, LoanDate, ExpectedReturn) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, loan.getInventory().getId());
            stmt.setInt(2, loan.getUser().getId());
            stmt.setDate(3, loan.getLoanDate());
            stmt.setDate(4, loan.getExpectedReturnDate());
            stmt.executeUpdate();
        }
        inventoryRepository.borrowedInventoryStatus(loan.getInventory().getId());

        return loan;
    }

    public Loan updateLoan(Loan loan) throws SQLException {
        String sql = "UPDATE Loans SET ActualReturnDate = ? WHERE ID_Inventory = ? AND ID_User = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, loan.getActualReturnDate());
            stmt.setInt(2, loan.getInventory().getId());
            stmt.setInt(3, loan.getUser().getId());
            stmt.executeUpdate();
        }
        inventoryRepository.availableInventoryStatus(loan.getInventory().getId());

        return loan;
    }

    public List<Loan> getLoansActives() throws SQLException {
        List<Loan> loanList = new ArrayList<>();
        String sql = """
                SELECT l.*
                FROM Loans l
                JOIN Inventory i ON l.ID_Inventory = i.ID
                WHERE i.Status = 'Borrowed';
                """;
        try (PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Loan loan = new Loan(
                        rs.getInt("ID_Inventory"),
                        rs.getInt("ID_User"),
                        rs.getDate("LoanDate"),
                        rs.getDate("ExpectedReturn"),
                        rs.getDate("ActualReturnDate")
                );
                loanList.add(loan);
            }
        }
        return loanList;
    }

    public Loan getLoan (Loan loan, int id) throws SQLException {
        String sql = "SELECT * FROM loans WHERE ID_inventory = ? OR ID_User = ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Loan(
                            rs.getInt("ID_Inventory"),
                            rs.getInt("ID_User"),
                            rs.getDate("LoanDate"),
                            rs.getDate("ExpectedReturn"),
                            rs.getDate("ActualReturnDate")
                    );
                }
            }
        }
        return null;
    }
}
