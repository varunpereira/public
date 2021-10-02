package main.model.admin;

import main.appsetup.SQLConnection;

import java.sql.Connection;

//purpose: data logic for updating and deleting admins
public class UpdateDeleteAdminModel {
    private Connection connection;

    //purpose: initialise member variables
    public UpdateDeleteAdminModel() {
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
