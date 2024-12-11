package br.com.mddeveloper.Repository;

import br.com.mddeveloper.Model.Inventory;
import br.com.mddeveloper.Model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public int saveUser(User user) throws SQLException {
        String sql = "INSERT INTO Users(Name, Email, Address, Phone, BirthDate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getPhone());
            stmt.setDate(5, new Date(user.getBirthDate().getTime()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                throw new SQLException("Falha ao obter o ID gerado.");
            }
        }
    }

    public void updateUserRepository(User user) throws SQLException {
        String sql = "UPDATE Users SET Name = ?, Email = ?, Address = ?, Phone = ?, BirthDate = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getAddress());
            stmt.setString(4, user.getPhone());
            stmt.setDate(5, user.getBirthDate());
            stmt.executeUpdate();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getDate("BirthDate")
                );
                userList.add(user);
            }
        }
        return userList;
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM Users WHERE ID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(
                            rs.getInt("ID"),
                            rs.getString("Name"),
                            rs.getString("Email"),
                            rs.getString("Address"),
                            rs.getString("Phone"),
                            rs.getDate("BirthDate")
                    );
                }
            }
        }
        return null;
    }
}
