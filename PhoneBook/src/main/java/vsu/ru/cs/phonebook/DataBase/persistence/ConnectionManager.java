package vsu.ru.cs.phonebook.DataBase.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private String DB_URL;
    private String DB_USER;
    private String DB_PASS;

    private static ConnectionManager instance;

    private ConnectionManager() {
        this("jdbc:h2:~/test", "sa", "");
    }

    private ConnectionManager(String DB_URL, String DB_USER, String DB_PASS) {
        try {
            Class.forName("org.h2.Driver");
            this.DB_URL = DB_URL;
            this.DB_USER = DB_USER;
            this.DB_PASS = DB_PASS;
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to find DB Driver: " + e.getMessage());
        }
    }

    public static ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }
        return instance;
    }

    public static ConnectionManager getInstance(String DB_URL, String DB_USER, String DB_PASS) {
        if (instance == null) {
            instance = new ConnectionManager(DB_URL, DB_USER, DB_PASS);
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }
}
