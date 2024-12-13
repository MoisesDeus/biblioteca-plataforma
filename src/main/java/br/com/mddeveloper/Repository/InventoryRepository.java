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

    public int saveInventoryItem(Inventory inventory) throws SQLException {
        String sql = "INSERT INTO Inventory (ID_Catalog, Status) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inventory.getIdCatalog());
            stmt.setString(2, inventory.getStatus());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID gerado.");
            }
        }
    }

    public void availableInventoryStatus(int inventoryId) throws SQLException {
        String sql = "UPDATE Inventory SET Status = 'Available' WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inventoryId);
            stmt.executeUpdate();
        }
    }

    public void borrowedInventoryStatus(int inventoryId) throws SQLException {
        String sql = "UPDATE Inventory SET Status = 'Borrowed' WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, inventoryId);
            stmt.executeUpdate();
        }
    }

    public List<Inventory> getAllInventory() throws SQLException {
        List<Inventory> inventoryList = new ArrayList<>();
        String sql = "SELECT * FROM Inventory";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Inventory inventory = new Inventory(
                        rs.getInt("ID"),
                        rs.getInt("ID_Catalog"),
                        rs.getString("Status")
                );
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
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

    public String isAvailable(int id) throws SQLException{
        String sql = "SELECT Status FROM Inventory WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Status");
                }
            }
        }
        return null;
    }
}

