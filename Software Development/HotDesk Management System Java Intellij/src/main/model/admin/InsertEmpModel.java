package main.model.admin;

import main.appsetup.SQLConnection;

import java.sql.Connection;

//purpose: data logic for adding new employees
public class InsertEmpModel {
    private Connection connection;

    //purpose: initialise member variables
    public InsertEmpModel() {
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    //purpose: check database connection
    public Boolean isDbConnected() {
        try {
            return !connection.isClosed();
        } catch (Exception e) {
            return false;
        }
    }

}
