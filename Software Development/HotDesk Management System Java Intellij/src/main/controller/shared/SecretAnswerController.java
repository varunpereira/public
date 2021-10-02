package main.controller.shared;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.model.emp.BookingModel;
import main.model.shared.SecretAnswerModel;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//purpose: application logic for secret question and answer page
public class SecretAnswerController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private TextField username;
    @FXML
    private Label secretQ;
    @FXML
    private TextField secretA;
    @FXML
    private Label error;
    private UpdatePasswordController updatePasswordController = new UpdatePasswordController();
    private BookingModel bookingModel = new BookingModel();
    private SecretAnswerModel secretAnswerModel = new SecretAnswerModel();

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bookingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    //purpose: loads the secret question and answer page
    public void SecretAnswerScene(ActionEvent clickedButton) throws IOException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/shared/SecretAnswerView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: sets the secret question based of searched username
    public void getQuestion() {
        try {
            secretAnswerModel.findQuestion(username.getText());
            if (secretAnswerModel.getQuestion() != null) {
                secretQ.setText("Secret Question: " + secretAnswerModel.getQuestion());
            } else {
                error.setText("No secret question exists for given username.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //purpose: check if inputted secret answer matches the database
    public void checkAnswerTrue(ActionEvent clickedButton) throws IOException {
        try {
            if (secretAnswerModel.answerTrue(username.getText(), secretA.getText())) {
                error.setText("CORRECT ANSWER!");
                secretAnswerModel.willResetPassword(username.getText());
                updatePasswordController.UpdatePasswordScene(clickedButton);
            } else {
                error.setText("Wrong answer for given username.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //purpose: button that goes back to login page
    public void goToLogin(ActionEvent clickedButton) throws IOException, SQLException {
        LoginController loginController = new LoginController();
        loginController.LoginScene(clickedButton);
    }

}
