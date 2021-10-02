package main.model.admin;

import main.appsetup.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//purpose: data logic for managing booking requests
public class BookingRequestsModel {
    private Connection connection;
    private String username;

    //purpose: initialise member variables
    public BookingRequestsModel() {
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

    //purpose: returns true if desk info from parameter is found in database
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

    //purpose: updates employee's blue desk to red desk since its now confirmed
    public void updateCurrentDeskToRed(String deskUsername, String deskColourConfirmed) throws SQLException {
        String query = "UPDATE Desks SET desk_colour = (?) WHERE user_username = (?) and desk_current = (?) and desk_colour = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, deskColourConfirmed);
            preparedStatement.setString(2, deskUsername);
            preparedStatement.setString(3, "1");
            preparedStatement.setString(4, "blue");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: removes employee request from database since request rejected by admin
    public void deleteCurrentBooking(String date, int location) throws SQLException {
        String query = "DELETE FROM Desks WHERE desk_current= ? AND desk_date = ? AND desk_location = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "1");
            preparedStatement.setString(2, date);
            preparedStatement.setInt(3, location);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: adds one orange covid desk to database
    public void covidDeskFinalised(String covidUsername, String deskDate, int deskLocation, String deskColour) throws SQLException {
        String query = "INSERT INTO Desks (user_username,desk_date,desk_location,desk_colour,desk_current) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, covidUsername);
            preparedStatement.setString(2, deskDate);
            preparedStatement.setInt(3, deskLocation);
            preparedStatement.setString(4, deskColour);
            preparedStatement.setString(5, "0");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose:retrieves employee username
    public String getUsername() {
        return username;
    }

    //purpose:changes employee username
    public void setUsername(String newUsername) {
        username = newUsername;
    }

    //purpose:finds employee username of their current booking
    public void findUsername(String tomorrowsDate, int deskLocation) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select user_username from Desks where desk_date = (?) and desk_location = (?)"
                + " and desk_current = (?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tomorrowsDate);
            preparedStatement.setInt(2, deskLocation);
            preparedStatement.setString(3, "1");
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
}
