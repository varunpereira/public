package main.model.emp;

import main.appsetup.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//purpose: data logic for employee home page
public class EmpHomeModel {
    private Connection connection;
    private String username;
    private String latestLocation;
    private String latestDate;

    //purpose: initialise member variables
    public EmpHomeModel() {
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

    //purpose: find username of employee who's currently logged in
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

    //purpose: find current confirmed booking of employee
    public boolean findLatestConfirmedBooking(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select desk_location,desk_date from Desks where user_username = ? and desk_current = ? and desk_colour = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, "1");
            preparedStatement.setString(3, "red");
            resultSet = preparedStatement.executeQuery();
            setLocation(resultSet.getString("desk_location"));
            setDate(resultSet.getString("desk_date"));
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

    //purpose: check if employee's current booking has passed today's date
    public boolean checkCurrentDeskDatePassed(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select desk_location,desk_date from Desks where user_username = ? and desk_current = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, "1");
            resultSet = preparedStatement.executeQuery();
            setLocation(resultSet.getString("desk_location"));
            setDate(resultSet.getString("desk_date"));
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

    //purpose: find the requested current booking of employee
    public boolean findRequestedBooking(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select desk_location,desk_date from Desks where user_username = ? and desk_current = ? and desk_colour = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, "1");
            preparedStatement.setString(3, "blue");
            resultSet = preparedStatement.executeQuery();
            setLocation(resultSet.getString("desk_location"));
            setDate(resultSet.getString("desk_date"));
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

    //purpose: change employee username
    public void setUsername(String newUsername) {
        username = newUsername;
    }

    //purpose: retrieve employee username
    public String getUsername() {
        return username;
    }

    //purpose: change employee desk location
    public void setLocation(String newLocation) {
        latestLocation = newLocation;
    }

    //purpose: retrieve employee desk location
    public String getLocation() {
        return latestLocation;
    }

    //purpose: change employee's desk date
    public void setDate(String newDate) {
        latestDate = newDate;
    }

    //purpose: set empp=loyee's desk date
    public String getDate() {
        return latestDate;
    }

    //purpose: delete employee's current booking
    public void deleteCurrentBooking(String currentDeskUsername) throws SQLException {
        String query = "DELETE FROM Desks WHERE user_username = ? AND desk_current= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, currentDeskUsername);
            preparedStatement.setString(2, "1");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
