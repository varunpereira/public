package main.model.emp;

import main.appsetup.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//purpose: data logic for updating employee details via employee account
public class UpdateDetailsModel {
    private Connection connection;
    private String username;

    //purpose: initialise member variables
    public UpdateDetailsModel() {
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

    //purpose: before updating username check if username already taken by someone else
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

    //purpose: retrieve employee username
    public String getUsername() {
        return username;
    }

    //purpose: change employee username
    public void setUsername(String newUsername) {
        username = newUsername;
    }

    //purpose: find username of employee that's currently logeed in
    public void findUsername() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select user_username from Users where user_currentlyloggedin = (?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "1");
            resultSet = preparedStatement.executeQuery();
            String username = resultSet.getString("user_username");
            setUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    //purpose: update employee's ID
    public void updateEmpID(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_userID = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's first name
    public void updateFirstName(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_firstname = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's last name
    public void updateLastName(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_lastname = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's age
    public void updateAge(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_age = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's company role
    public void updateRole(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_role = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's username in Users table
    public void updateUsersUsername(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_username = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's username in Desks table
    public void updateDesksUsername(String oldUsername, String newUsername) {
        String query = "UPDATE Desks SET " +
                "user_username = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, oldUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's password
    public void updatePassword(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_password = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's secret question
    public void updateSecretQ(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_secretq = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: update employee's secret answer
    public void updateSecretA(String givenUsername, String newDetail) {
        String query = "UPDATE Users SET " +
                "user_secreta = ?"
                + " WHERE user_username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newDetail);
            preparedStatement.setString(2, givenUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}




