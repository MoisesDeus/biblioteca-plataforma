package br.com.mddeveloper.Setup;

import br.com.mddeveloper.Util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void initialize() {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String createCatalogTable = """
                     CREATE TABLE IF NOT EXISTS Catalog (
                                        ID INTEGER PRIMARY KEY AUTOINCREMENT,
                                        Title TEXT NOT NULL,
                                        Author TEXT NOT NULL,
                                        Genre TEXT,
                                        Year INTEGER,
                                        Pages INTEGER
                                    );
                    """;

            String createInventoryTable = """
                     CREATE TABLE IF NOT EXISTS Inventory (
                                        ID INTEGER PRIMARY KEY AUTOINCREMENT,
                                        ID_Catalog INTEGER NOT NULL,
                                        Status TEXT CHECK (Status IN ('Available', 'Borrowed')),
                                        FOREIGN KEY (ID_Catalog) REFERENCES Catalog(ID)
                                    );
                    """;

            String createUserTable = """
                    CREATE TABLE IF NOT EXISTS Users (
                                        ID INTEGER PRIMARY KEY AUTOINCREMENT,
                                        Name TEXT NOT NULL,
                                        Address TEXT,
                                        Email TEXT UNIQUE,
                                        Phone TEXT,
                                        BirthDate DATE
                                    );
                    """;

            String createLoansTable = """
                     CREATE TABLE IF NOT EXISTS Loans (
                                        ID_Inventory INTEGER NOT NULL,
                                        ID_User INTEGER NOT NULL,
                                        LoanDate DATE NOT NULL,
                                        ExpectedReturnDate DATE NOT NULL,
                                        ActualReturnDate DATE,
                                        FOREIGN KEY (ID_Inventory) REFERENCES Inventory(ID),
                                        FOREIGN KEY (ID_User) REFERENCES Users(ID)
                                    );
                    """;

            statement.execute(createCatalogTable);
            statement.execute(createInventoryTable);
            statement.execute(createUserTable);
            statement.execute(createLoansTable);

            String setCatalogInitialValue = "INSERT INTO sqlite_sequence (name, seq) VALUES ('Catalog', 1000);";
            String setInventoryInitialValue = "INSERT INTO sqlite_sequence (name, seq) VALUES ('Inventory', 2000);";
            String setUsersInitialValue = "INSERT INTO sqlite_sequence (name, seq) VALUES ('Users', 3000);";

            statement.execute(setCatalogInitialValue);
            statement.execute(setInventoryInitialValue);
            statement.execute(setUsersInitialValue);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
