package br.com.mddeveloper.Repository;

import br.com.mddeveloper.Model.Catalog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CatalogRepository {
    private Connection connection;

    public CatalogRepository(Connection connection) {
        this.connection = connection;
    }

    public int saveCatalogItem(Catalog catalog) throws SQLException {
        String sql = "INSERT INTO Catalog (Title, Author, Genre, Year, Pages) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, catalog.getTitle());
            stmt.setString(2, catalog.getAuthor());
            stmt.setString(3, catalog.getGenre());
            stmt.setInt(4, catalog.getYear());
            stmt.setInt(5, catalog.getPages());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID gerado.");
            }
        }
    }

    public void updateCatalogItem(Catalog catalog) throws SQLException {
        String sql = "UPDATE Catalog SET Title = ?, Author = ?, Genre = ?, Year = ?,  Pages = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, catalog.getTitle());
            stmt.setString(2, catalog.getAuthor());
            stmt.setString(3, catalog.getGenre());
            stmt.setInt(4, catalog.getYear());
            stmt.setInt(5, catalog.getPages());
            stmt.setInt(6, catalog.getId());
            stmt.executeUpdate();
        }
    }

    public void deleteCatalogItem(int id) throws SQLException {
        String sql = "DELETE FROM Catalog WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Catalog> getAllCatalogItems() throws SQLException {
        List<Catalog> catalogList = new ArrayList<>();
        String sql = "SELECT * FROM Catalog";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Catalog catalog = new Catalog(
                        rs.getInt("ID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Genre"),
                        rs.getInt("Year"),
                        rs.getInt("Pages")
                );
                catalogList.add(catalog);
            }
        }
        return catalogList;
    }

    public Catalog getCatalogItemById(int id) throws SQLException {
        String sql = "SELECT * FROM Catalog WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Catalog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Author"),
                            rs.getString("Genre"),
                            rs.getInt("Year"),
                            rs.getInt("Pages")
                    );
                }
            }
        }
        return null;
    }

    public List<Catalog> getCatalogItemsByTitle(String title) throws SQLException {
        List<Catalog> catalogList = new ArrayList<>();
        String sql = "SELECT * FROM Catalog WHERE Title LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + title + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Catalog catalog = new Catalog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Author"),
                            rs.getString("Genre"),
                            rs.getInt("Year"),
                            rs.getInt("Pages")
                    );
                    catalogList.add(catalog);
                }
            }
        }
        return catalogList;
    }

    public List<Catalog> getCatalogItemsByAuthor(String author) throws SQLException {
        List<Catalog> catalogList = new ArrayList<>();
        String sql = "SELECT * FROM Catalog WHERE Author LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + author + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Catalog catalog = new Catalog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Author"),
                            rs.getString("Genre"),
                            rs.getInt("Year"),
                            rs.getInt("Pages")
                    );
                    catalogList.add(catalog);
                }
            }
        }
        return catalogList;
    }

    public List<Catalog> getCatalogItemsByGenre(String genre) throws SQLException {
        List<Catalog> catalogList = new ArrayList<>();
        String sql = "SELECT * FROM Catalog WHERE Genre LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + genre + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Catalog catalog = new Catalog(
                            rs.getInt("ID"),
                            rs.getString("Title"),
                            rs.getString("Author"),
                            rs.getString("Genre"),
                            rs.getInt("Year"),
                            rs.getInt("Pages")
                    );
                    catalogList.add(catalog);
                }
            }
        }
        return catalogList;
    }
}
