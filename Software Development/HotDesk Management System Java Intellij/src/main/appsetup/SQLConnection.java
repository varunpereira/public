package main.appsetup;

import java.sql.*;


public class SQLConnection {

    //purpose: connect program to right database
    public static Connection connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:assignment.db");
            return connection;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
