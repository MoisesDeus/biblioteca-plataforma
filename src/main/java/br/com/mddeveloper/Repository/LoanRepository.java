package br.com.mddeveloper.Repository;

import br.com.mddeveloper.Model.Inventory;
import br.com.mddeveloper.Model.Loan;
import br.com.mddeveloper.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {
    private Connection connection;
    private InventoryRepository inventoryRepository;
    private UserRepository userRepository;

    public LoanRepository(Connection connection, InventoryRepository inventoryRepository, UserRepository userRepository) {
        this.connection = connection;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }



    public void saveLoan(Loan loan) throws SQLException {
        String sql = "INSERT INTO Loans (ID_Inventory, ID_User, LoanDate) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, loan.getInventory().getId());
            stmt.setInt(2, loan.getUser().getId());
            stmt.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            stmt.executeUpdate();
        }
        inventoryRepository.borrowedInventoryStatus(loan.getInventory().getId());
    }

    public void updateLoan(Date returnDate, Loan loan) throws SQLException {
        String sql = "UPDATE Loans SET ActualReturnDate = ? WHERE ID_Inventory = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, returnDate);
            stmt.setInt(2, loan.getInventory().getId());
            stmt.executeUpdate();
        }
        inventoryRepository.availableInventoryStatus(loan.getInventory().getId());
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
                        rs.getDate("ExpectedReturnDate"),
                        rs.getDate("ActualReturnDate")
                );
                loanList.add(loan);
            }
        }
        return loanList;
    }

    public List<Loan> getLoanList (int id) throws SQLException {
        List<Loan> loanActiveList = new ArrayList<>();
        String sql = "SELECT * FROM Loans WHERE ID_User = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Inventory inventory = inventoryRepository.getInventoryItemById(rs.getInt("ID_Inventory"));
                    User user = userRepository.getUserById(rs.getInt("ID_User"));
                    Loan loan = new Loan(
                    inventory,
                    user,
                    rs.getDate("LoanDate"),
                    rs.getDate("ExpectedReturnDate"),
                    rs.getDate("ActualReturnDate")
                    );
                    loanActiveList.add(loan);
                }
            }
        }
        return loanActiveList;
    }

    public Loan getLoan (int id) throws SQLException {
        String sql = "SELECT * FROM Loans WHERE ID_Inventory = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Loan(
                            rs.getInt("ID_Inventory"),
                            rs.getInt("ID_User"),
                            rs.getDate("LoanDate"),
                            rs.getDate("ExpectedReturnDate"),
                            rs.getDate("ActualReturnDate")
                    );
                }
            }
        }
        return null;
    }
}
