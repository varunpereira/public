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
import main.model.shared.UpdatePasswordModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//purpose: application logic for resetting password page
public class UpdatePasswordController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private TextField password1;
    @FXML
    private TextField password2;
    @FXML
    private Label error;
    private UpdatePasswordModel updatePasswordModel = new UpdatePasswordModel();
    private String userUsername;

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userUsername = "";
    }

    //purpose: loads reset password page
    public void UpdatePasswordScene(ActionEvent clickedButton) throws IOException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/shared/UpdatePasswordView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: allows user to change their password
    public void passwordChange(ActionEvent clickedButton) {
        if (password1.getText().trim().isEmpty()) {
            error.setText("Password can not be empty!");
        } else {
            if (password1.getText().equals(password2.getText())) {
                try {
                    updatePasswordModel.hasResetPassword(password1.getText());
                    EmpHomeController empHomeController = new EmpHomeController();
                    empHomeController.EmpHomeScene(clickedButton);
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            } else {
                error.setText("Passwords do not match!");
            }
        }
    }

    //purpose: button that goes back to home page
    public void goToLogin(ActionEvent clickedButton) throws IOException, SQLException {
        updatePasswordModel.resetCurrentlyLoggedIn();
        LoginController loginController = new LoginController();
        loginController.LoginScene(clickedButton);
    }

}
