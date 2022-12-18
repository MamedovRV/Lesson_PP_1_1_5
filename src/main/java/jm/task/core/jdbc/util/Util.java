package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    Connection connection;
    private final String url = "jdbc:mysql://127.0.0.1:3306/users";
    private static final String user = "root";
    private static final String pass = "Cvtybnmgfhjkm1!";

    public Util(){
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection() {
        return connection;
    }

    public String getUrl() {
        return url;
    }

    public static String getPass() {
        return pass;
    }

    public static String getUser() {
        return user;
    }
}
