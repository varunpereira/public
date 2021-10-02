package main.controller.emp;

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
import main.model.emp.UpdateDetailsModel;
import main.model.shared.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//purpose: application logic for updating account details for employee
public class UpdateDetailsController implements Initializable {
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
    private UpdateDetailsModel updateDetailsModel = new UpdateDetailsModel();

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        errorMessage.setText("");
    }

    //purpose: loads the employee update details page
    public void UpdateDetailsScene(ActionEvent clickedButton) throws IOException, SQLException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/emp/UpdateDetailsView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: updates employee's id
    public void updateEmpID(ActionEvent clickedButton) throws IOException, SQLException {
        if (empID.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            updateDetailsModel.findUsername();
            updateDetailsModel.updateEmpID(updateDetailsModel.getUsername(), empID.getText());
            errorMessage.setText("Updated!");
        }
    }

    //purpose: updates employee's first name
    public void updateFirstName(ActionEvent clickedButton) throws IOException, SQLException {
        if (firstName.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            updateDetailsModel.findUsername();
            updateDetailsModel.updateFirstName(updateDetailsModel.getUsername(), firstName.getText());
            errorMessage.setText("Updated!");
        }
    }

    //purpose: updates employee's last name
    public void updateLastName(ActionEvent clickedButton) throws IOException, SQLException {
        if (lastName.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            updateDetailsModel.findUsername();
            updateDetailsModel.updateLastName(updateDetailsModel.getUsername(), lastName.getText());
            errorMessage.setText("Updated!");
        }
    }

    //purpose: updates employee's age
    public void updateAge(ActionEvent clickedButton) throws IOException, SQLException {
        if (age.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            updateDetailsModel.findUsername();
            updateDetailsModel.updateAge(updateDetailsModel.getUsername(), age.getText());
            errorMessage.setText("Updated!");
        }
    }

    //purpose: updates employee's role
    public void updateRole(ActionEvent clickedButton) throws IOException, SQLException {
        if (role.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            updateDetailsModel.findUsername();
            updateDetailsModel.updateRole(updateDetailsModel.getUsername(), role.getText());
            errorMessage.setText("Updated!");
        }
    }

    //purpose: updates employee's username
    public void updateUsername(ActionEvent clickedButton) throws SQLException {
        if (username.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            if (updateDetailsModel.usernameExists(username.getText())) {
                errorMessage.setText("Username already exists!");
            } else {
                updateDetailsModel.findUsername();
                String oldUsername = updateDetailsModel.getUsername();
                updateDetailsModel.updateDesksUsername(oldUsername, username.getText());
                updateDetailsModel.updateUsersUsername(oldUsername, username.getText());
                errorMessage.setText("Updated!");
            }
        }
    }

    //purpose: updates employee's password
    public void updatePassword(ActionEvent clickedButton) throws IOException, SQLException {
        if (password.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            updateDetailsModel.findUsername();
            updateDetailsModel.updatePassword(updateDetailsModel.getUsername(), password.getText());
            errorMessage.setText("Updated!");
        }
    }

    //purpose: updates employee's secret question
    public void updateSecretQ(ActionEvent clickedButton) throws IOException, SQLException {
        if (secretQ.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            updateDetailsModel.findUsername();
            updateDetailsModel.updateSecretQ(updateDetailsModel.getUsername(), secretQ.getText());
            errorMessage.setText("Updated!");
        }

    }

    //purpose: updates employee's secret answer
    public void updateSecretA(ActionEvent clickedButton) throws IOException, SQLException {
        if (secretA.getText().trim().isEmpty()) {
            errorMessage.setText("Nothing entered!");
        } else {
            updateDetailsModel.findUsername();
            updateDetailsModel.updateSecretA(updateDetailsModel.getUsername(), secretA.getText());
            errorMessage.setText("Updated!");
        }
    }

    //purpose: button that takes you back to employee home page
    public void goHome(ActionEvent clickedButton) throws IOException, SQLException {
        EmpHomeController empHomeController = new EmpHomeController();
        empHomeController.EmpHomeScene(clickedButton);
    }

    //purpose: button that logs you out of your account
    public void Logout(ActionEvent clickedButton) throws IOException, SQLException {
        LoginModel loginModel = new LoginModel();
        loginModel.allLoggedOff();
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/shared/LoginView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

}
