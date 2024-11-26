package br.com.mddeveloper.Repository;

import br.com.mddeveloper.Model.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryRepository {
    private Connection connection;

    public InventoryRepository(Connection connection) {
        this.connection = connection;
    }

    public Inventory saveInventoryItem( Inventory inventory, int catalogId, String status) throws SQLException {
        String sql = "INSERT INTO Inventory (ID_Catalog, Status) VALUES (?, 'Available')";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, catalogId);
            stmt.setString(2, status);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return inventory;
    }

    public void availableInventoryStatus(int inventoryId) throws SQLException {
        String sql = "UPDATE Inventory SET Status = 'Available' WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inventoryId);
            stmt.executeUpdate();
        }
    }

    public void borrowedInventoryStatus(int inventoryId) throws SQLException {
        String sql = "UPDATE Inventory SET Status = 'Borrowed'";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inventoryId);
            stmt.executeUpdate();
        }
    }

//    public void deleteInventoryItem(int id) throws SQLException {
//        String sql = "DELETE FROM Inventory WHERE ID = ?";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, id);
//            stmt.executeUpdate();
//        }
//    }

    public List<Inventory> getInventoryItemsByStatus(String Status) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE Status = ?";
        List<Inventory> inventoryList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, Status);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Inventory inventory = new Inventory(
                            rs.getInt("ID"),
                            rs.getInt("ID_Catalog"),
                            rs.getString("Status")
                    );
                    inventoryList.add(inventory);
                }
            }
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryItemsByCatalogId(int catalogId) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE ID_Catalog = ?";
        List<Inventory> inventoryList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, catalogId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Inventory inventory = new Inventory(
                            rs.getInt("ID"),
                            rs.getInt("ID_Catalog"),
                            rs.getString("Status")
                    );
                    inventoryList.add(inventory);
                }
            }
        }
        return inventoryList;
    }

    public Inventory getInventoryItemById(int id) throws SQLException {
        String sql = "SELECT * FROM Inventory WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Inventory(
                            rs.getInt("ID"),
                            rs.getInt("ID_Catalog"),
                            rs.getString("Status")
                    );
                }
            }
        }
        return null;
    }
}

