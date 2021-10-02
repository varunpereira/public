package main.controller.admin;

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
import main.model.admin.InsertEmpModel;
import main.model.shared.LoginModel;
import main.model.shared.RegisterModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//purpose: application logic for adding employee page
public class InsertEmpController implements Initializable {
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

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InsertEmpModel insertEmpModel = new InsertEmpModel();
        if (insertEmpModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    //purpose: loads the add employee page
    public void InsertEmpScene(ActionEvent clickedButton) throws IOException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/admin/InsertEmpView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: button thats takes you back to admin home page
    public void goAdminHome(ActionEvent clickedButton) throws IOException, SQLException {
        AdminHomeController adminHomeController = new AdminHomeController();
        adminHomeController.AdminHomeScene(clickedButton);
    }

    //purpose: button that log your account out
    public void Logout(ActionEvent clickedButton) throws IOException, SQLException {
        LoginModel loginModel = new LoginModel();
        loginModel.allLoggedOff();
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/shared/LoginView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: checks if a username already exists in database
    public void checkUsernameExists(ActionEvent clickedButton) throws IOException {
        RegisterModel registerModel = new RegisterModel();
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
                    registerModel.accountRegisteredByAdmin("emp", empID.getText(), firstName.getText(), lastName.getText(), age.getText(), role.getText(), username.getText(), password.getText(), secretQ.getText(), secretA.getText());
                    AdminHomeController adminHomeController = new AdminHomeController();
                    adminHomeController.AdminHomeScene(clickedButton);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
