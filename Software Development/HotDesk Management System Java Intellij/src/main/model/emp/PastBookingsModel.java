package main.model.emp;

import main.appsetup.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//purpose: data logic for employee booking history
public class PastBookingsModel {
    private Connection connection;
    private String username;
    private List<String> pastLocations = new ArrayList<>();
    private List<String> pastDates = new ArrayList<>();

    //purpose: initialise member variables
    public PastBookingsModel() {
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

    //purpose: find username of employee that's currently logged in
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

    //purpose: change employee's username
    public void setUsername(String newUsername) {
        username = newUsername;
    }

    //purpose: retrieve employee's username
    public String getUsername() {
        return username;
    }

    //purpose: find past bookings of employee and set them
    public boolean findPastBookings(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select desk_location,desk_date from Desks where user_username = ? and desk_current = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, "0");
            resultSet = preparedStatement.executeQuery();
            int length = 0;
            while (resultSet.next()) {
                length += 1;
                pastLocations.add(resultSet.getString("desk_location"));
                pastDates.add(resultSet.getString("desk_date"));
            }
            if (length > 0) {
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

    //purpose: retrieve employee's past desk locations
    public List<String> getLocation() {
        return pastLocations;
    }

    //purpose: retrieve employee's past desk dates
    public List<String> getDate() {
        return pastDates;
    }

}
