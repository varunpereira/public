package main.model.admin;

import main.appsetup.SQLConnection;

import java.io.FileWriter;
import java.sql.*;

//purpose: data logic for admin home page
public class AdminHomeModel {
    //CHANGE CSV FILE DIRECTORY HERE!!
    private String BookingInfoFileLocation = "C:\\Users\\varun\\Desktop\\BookingInfo.csv";
    private String EmployeeInfoFileLocation = "C:\\Users\\varun\\Desktop\\EmployeeInfo.csv";

    private Connection connection;

    //purpose: initialise member variables
    public AdminHomeModel() {
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

    //purpose: downloads all employees confirmed booking history in a csv file
    public void downloadBookingInfo() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            FileWriter fw = new FileWriter(BookingInfoFileLocation);
            String query = "select * from Desks where desk_colour = 'red'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            String[] colHeadings = new String[6];
            for (int i = 0; i < colHeadings.length; i += 1) {
                colHeadings[i] = resultSetMetaData.getColumnName(i + 1);
                fw.append(colHeadings[i]);
                if (i == colHeadings.length - 1) {
                    fw.append('\n');
                } else {
                    fw.append(',');
                }
            }
            while (resultSet.next()) {
                fw.append(resultSet.getString(1));
                fw.append(',');
                fw.append(resultSet.getString(2));
                fw.append(',');
                fw.append(resultSet.getString(3));
                fw.append(',');
                fw.append(resultSet.getString(4));
                fw.append(',');
                fw.append(resultSet.getString(5));
                fw.append(',');
                fw.append(resultSet.getString(6));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: downloads all employees and their details in a csv file
    public void downloadEmployeeInfo() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            FileWriter fw = new FileWriter(EmployeeInfoFileLocation);

            String query = "SELECT u.user_username,u.user_type,u.user_firstname,"
                    + "u.user_lastname,u.user_age,u.user_role,u.user_password,u.user_secretq,"
                    + "u.user_secreta,u.user_currentlyloggedin,u.user_userID "
                    + "FROM Users u WHERE u.user_type='emp'";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            String[] colHeadings = new String[11];
            for (int i = 0; i < colHeadings.length; i += 1) {
                colHeadings[i] = resultSetMetaData.getColumnName(i + 1);
                fw.append(colHeadings[i]);
                if (i == colHeadings.length - 1) {
                    fw.append('\n');
                } else {
                    fw.append(',');
                }
            }
            while (resultSet.next()) {
                for (int colNum = 1; colNum < (colHeadings.length + 1); colNum += 1) {
                    if (colNum == colHeadings.length) {
                        fw.append(resultSet.getString(colNum));
                        fw.append('\n');
                    } else {
                        fw.append(resultSet.getString(colNum));
                        fw.append(',');
                    }
                }
            }
            fw.flush();
            fw.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
