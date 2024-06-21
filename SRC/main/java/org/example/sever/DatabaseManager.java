package org.example.server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
public class DatabaseManager {
    private Connection connection;

    public DatabaseManager(String databaseUrl) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseUrl);
            printAllTables();
            System.out.println("connect successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void printAllTables() {
        if (connection == null) {
            System.err.println("No active database connection");
            return;
        }

        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});

            boolean foundTables = false;
            while (tables.next()) {
                foundTables = true;
                String tableName = tables.getString("TABLE_NAME");
                System.out.println("Table: " + tableName);
            }
            tables.close();

            if (!foundTables) {
                System.out.println("No tables found in the database.");
            }
        } catch (SQLException e) {
            System.err.println("Failed to retrieve table names: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        try {
            if (connection.isClosed()) {
//                connection.;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


}
