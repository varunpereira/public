package main.model.shared;

import main.appsetup.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//purpose: data logic for resetting password
public class UpdatePasswordModel {
    private Connection connection;

    //purpose: initialise member variables
    public UpdatePasswordModel() {
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

    //purpose: successfully reset user's password
    //then go to menu
    public void hasResetPassword(String password) throws SQLException {
        String query = "UPDATE Users SET user_currentlyloggedin = (?), user_password = (?) WHERE user_currentlyloggedin = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "1");
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, "2");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: logout all user accounts
    public void resetCurrentlyLoggedIn() throws SQLException {
        String query = "UPDATE Users SET user_currentlyloggedin = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "0");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
