package main.model.shared;

import main.appsetup.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//purpose: data logic for secret question and answer page
public class SecretAnswerModel {
    private Connection connection;
    private String question;

    //purpose: initialise member variables
    public SecretAnswerModel() {
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

    //purpose: retrieve user's secret question
    public String getQuestion() {
        return question;
    }

    //purpose: change user's secret answer
    public void setQuestion(String newQuestion) {
        question = newQuestion;
    }

    //purpose: find user's secret question based of their username
    public void findQuestion(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select user_secretq from Users where user_username = (?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            String question = resultSet.getString("user_secretq");
            setQuestion(question);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
    }

    //purpose: check if user got their secret question right
    public Boolean answerTrue(String username, String answer) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Users where user_username = (?) and user_secreta = (?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, answer);
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

    //purpose: allowing program to identify who is about to reset their password
    public void willResetPassword(String username) throws SQLException {
        String query = "UPDATE Users SET user_currentlyloggedin = (?) WHERE user_username = (?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(2, username);
            preparedStatement.setString(1, "2");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
