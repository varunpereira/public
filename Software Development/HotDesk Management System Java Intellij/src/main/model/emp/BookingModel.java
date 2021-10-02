package main.model.emp;

import main.appsetup.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//purpose: data logic for making a booking
public class BookingModel {
    private Connection connection;
    private String username;
    private String loggedInUsername;

    //purpose: initialise member variables
    public BookingModel() {
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

    //purpose: see if a certain desk and its info is already in database including desk colour
    public Boolean checkDeskColour(String date, int location, String color) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Desks where desk_date = ? and desk_location = ? and desk_colour = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, location);
            preparedStatement.setString(3, color);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String username = resultSet.getString("user_username");
                setUsername(username);
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

    //purpose: changing database so that employee no longer has a current desk
    public void resetLatestBooking(String userUsername) throws SQLException {
        String query = "UPDATE Desks SET desk_current = (?) WHERE user_username = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "0");
            preparedStatement.setString(2, userUsername);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: adding to database the requested desk by employee
    public void bookingConfirmed(String deskUsername, String deskColour, String deskDate, int deskLocation) throws SQLException {
        String query = "INSERT INTO Desks (desk_location,desk_date,desk_colour,user_username,desk_current) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, deskLocation);
            preparedStatement.setString(2, deskDate);
            preparedStatement.setString(3, deskColour);
            preparedStatement.setString(4, deskUsername);
            preparedStatement.setString(5, "1");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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

    //purpose: find username of employee that is currently logged in
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

    //purpose: check is employee has already sat at that same desk location before
    public boolean locationVisitedBefore(String username, int location) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Desks where user_username = ? and desk_location = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, location);
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

    //purpose: check if logged in employee's username and desk already exist in database, for bonus marks
    public boolean loggedInUsernameDeskLocationExists(String username, int location) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Desks where user_username = ? and desk_location = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, location);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String loggedInUsernameDeskDate = resultSet.getString("desk_date");
                setLoggedInUsernamesDate(loggedInUsernameDeskDate);
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

    //purpose: check if certain desk info already exists in database, for bonus marks
    public boolean locationDateVisitedBefore(String username, int location, String date) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Desks where user_username = ? and desk_location = ? and desk_date = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, location);
            preparedStatement.setString(3, date);
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

    //purpose: change username of employee who is currently logged in
    public void setLoggedInUsernamesDate(String newLoggedInUsername) {
        loggedInUsername = newLoggedInUsername;
    }

    //purpose: retrieve username of employee who is currently logged in
    public String getLoggedInUsernamesDate() {
        return loggedInUsername;
    }

}
