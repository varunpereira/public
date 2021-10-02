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
import main.controller.emp.EmpHomeController;
import main.model.emp.BookingModel;
import main.model.shared.RegisterModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//purpose: application logic for register page
public class RegisterController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private TextField empID;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField age;
    @FXML
    private TextField role;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField secretQ;
    @FXML
    private TextField secretA;
    @FXML
    private Label errorMessage;
    private RegisterModel registerModel = new RegisterModel();
    private EmpHomeController empHomeController = new EmpHomeController();
    private BookingModel bookingModel = new BookingModel();

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bookingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
        errorMessage.setText("");
    }

    //purpose: loads the register page
    public void RegisterScene(ActionEvent clickedButton) throws IOException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/shared/RegisterView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: validates the text field entries
    public void checkUsernameExists(ActionEvent clickedButton) throws IOException {
        if (empID.getText().trim().isEmpty() || firstName.getText().trim().isEmpty() || lastName.getText().trim().isEmpty() || age.getText().trim().isEmpty()
                || role.getText().trim().isEmpty() || username.getText().trim().isEmpty() || password.getText().trim().isEmpty() || secretQ.getText().trim().isEmpty() || secretA.getText().trim().isEmpty()) {
            errorMessage.setText("All fields required!");
        } else {
            try {
                if (username.getText().contains("_")) {
                    errorMessage.setText("Username can not contain underscore!");
                } else if (username.getText().trim().equals("null")) {
                    errorMessage.setText("Username can not be 'null'");
                } else if (username.getText().contains(" ")) {
                    errorMessage.setText("Username can not contain spaces!");
                } else if (registerModel.usernameExists(username.getText())) {
                    errorMessage.setText("Username already exists!");
                } else {
                    registerModel.accountConfirmed(empID.getText(), firstName.getText(), lastName.getText(), age.getText(), role.getText(), username.getText(), password.getText(), secretQ.getText(), secretA.getText());
                    empHomeController.EmpHomeScene(clickedButton);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //purpose: button which goes back to login page
    public void goToLogin(ActionEvent clickedButton) throws IOException, SQLException {
        LoginController loginController = new LoginController();
        loginController.LoginScene(clickedButton);
    }

}
