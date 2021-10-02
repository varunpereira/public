package main.model.shared;

import main.appsetup.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//purpose: data logic for employee registration
public class RegisterModel {
    private Connection connection;

    //purpose: initialise member variables
    public RegisterModel() {
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

    //purpose: check if a username already exists in database
    public Boolean usernameExists(String user) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Users where user_username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    //purpose: register a new user account of type employee
    public void accountConfirmed(String empID, String firstName, String lastName, String age, String role, String username, String password, String secretQ, String secretA) throws SQLException {
        String query = "INSERT INTO Users (user_type,user_firstname,user_lastname,user_age,user_role,user_username,user_password,user_secretq,user_secreta,user_currentlyloggedin,user_userID)"
                + " VALUES (?, ?, ?, ?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "emp");
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, age);
            preparedStatement.setString(5, role);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password);
            preparedStatement.setString(8, secretQ);
            preparedStatement.setString(9, secretA);
            preparedStatement.setString(10, "1");
            preparedStatement.setString(11, empID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: registering a user account via admin
    //only for Admin side of program, insertempcontroller
    public void accountRegisteredByAdmin(String type, String accountID, String firstName, String lastName, String age, String role, String username, String password, String secretQ, String secretA) throws SQLException {
        String query = "INSERT INTO Users (user_type,user_firstname,user_lastname,user_age,user_role,user_username,user_password,user_secretq,user_secreta,user_currentlyloggedin,user_userID)"
                + " VALUES (?, ?, ?, ?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, type);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, age);
            preparedStatement.setString(5, role);
            preparedStatement.setString(6, username);
            preparedStatement.setString(7, password);
            preparedStatement.setString(8, secretQ);
            preparedStatement.setString(9, secretA);
            preparedStatement.setString(10, "0");
            preparedStatement.setString(11, accountID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: deleting user account from Users table via admin
    //only for Admin side of program,updatedeleteempcontroller
    public void deleteUserFromUsersTable(String username) throws SQLException {
        String query = "DELETE FROM Users WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: deleting user account from Desks table via admin
    //only for Admin side of program,updatedeleteempcontroller
    public void deleteUserCurrentDesk(String givenUsername) {
        String query = "DELETE FROM Desks WHERE user_username = ? AND desk_current = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, givenUsername);
            preparedStatement.setString(2, "1");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
