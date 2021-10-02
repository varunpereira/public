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
import main.model.admin.UpdateDeleteEmpModel;
import main.model.emp.UpdateDetailsModel;
import main.model.shared.LoginModel;
import main.model.shared.RegisterModel;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//purpose: application logic for updating and deleting employees
public class UpdateDeleteEmpController implements Initializable {
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
    @FXML
    private TextField enteredUsername;
    private UpdateDetailsModel updateDetailsModel = new UpdateDetailsModel();

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UpdateDeleteEmpModel updateDeleteEmpModel = new UpdateDeleteEmpModel();
        if (updateDeleteEmpModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    //purpose: loads manage employee page
    public void UpdateDeleteEmpScene(ActionEvent clickedButton) throws IOException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/admin/UpdateDeleteEmpView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: takes you back to admin home page
    public void goHome(ActionEvent clickedButton) throws IOException, SQLException {
        AdminHomeController adminHomeController = new AdminHomeController();
        adminHomeController.AdminHomeScene(clickedButton);
    }

    //purpose: logs your account out
    public void Logout(ActionEvent clickedButton) throws IOException, SQLException {
        LoginModel loginModel = new LoginModel();
        loginModel.allLoggedOff();
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/shared/LoginView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: hyperlink to add employee page
    public void insertEmp(ActionEvent clickedButton) throws IOException {
        InsertEmpController insertEmpController = new InsertEmpController();
        insertEmpController.InsertEmpScene(clickedButton);
    }

    //purpose: delete searched employee's account
    public void deleteEmp(ActionEvent clickedButton) throws SQLException, IOException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            RegisterModel registerModel = new RegisterModel();
            registerModel.deleteUserFromUsersTable(enteredUsername.getText());
            registerModel.deleteUserCurrentDesk(enteredUsername.getText());
            AdminHomeController adminHomeController = new AdminHomeController();
            adminHomeController.AdminHomeScene(clickedButton);
        } else {
            if (enteredUsername.getText().trim().isEmpty()) {
                errorMessage.setText("No username entered!");
            } else {
                errorMessage.setText("Username not found!");
            }
        }
    }

    //purpose: check if admin username was valid
    public void getEnteredUsername(ActionEvent clickedButton) {
        try {
            if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
                errorMessage.setText("Username found!");
            } else {
                if (enteredUsername.getText().trim().isEmpty()) {
                    errorMessage.setText("No username entered!");
                } else {
                    errorMessage.setText("Username not found!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //purpose: updates searched employee's id
    public void updateEmpID(ActionEvent clickedButton) throws IOException, SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (empID.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else {
                updateDetailsModel.updateEmpID(enteredUsername.getText(), empID.getText());
                errorMessage.setText("Updated!");
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }
    }

    //purpose: updates searched employee's first name
    public void updateFirstName(ActionEvent clickedButton) throws IOException, SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (firstName.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else {
                updateDetailsModel.updateFirstName(enteredUsername.getText(), firstName.getText());
                errorMessage.setText("Updated!");
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }
    }

    //purpose: updates searched employee's last name
    public void updateLastName(ActionEvent clickedButton) throws IOException, SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (lastName.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else {
                updateDetailsModel.updateLastName(enteredUsername.getText(), lastName.getText());
                errorMessage.setText("Updated!");
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }
    }

    //purpose: updates searched employee's age
    public void updateAge(ActionEvent clickedButton) throws IOException, SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (age.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else {
                updateDetailsModel.updateAge(enteredUsername.getText(), age.getText());
                errorMessage.setText("Updated!");
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }
    }

    //purpose: updates searched employee's role
    public void updateRole(ActionEvent clickedButton) throws IOException, SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (role.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else {
                updateDetailsModel.updateRole(enteredUsername.getText(), role.getText());
                errorMessage.setText("Updated!");
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }
    }

    //purpose: updates searched employee's username
    public void updateUsername(ActionEvent clickedButton) throws SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (username.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else if (updateDetailsModel.usernameExists(username.getText())) {
                errorMessage.setText("Username already exists!");
            } else {
                updateDetailsModel.updateDesksUsername(enteredUsername.getText(), username.getText());
                updateDetailsModel.updateUsersUsername(enteredUsername.getText(), username.getText());
                errorMessage.setText("Updated!");
                enteredUsername.setText(username.getText());
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }

    }

    //purpose: updates searched employee's password
    public void updatePassword(ActionEvent clickedButton) throws IOException, SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (password.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else {
                updateDetailsModel.updatePassword(enteredUsername.getText(), password.getText());
                errorMessage.setText("Updated!");
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }
    }

    //purpose: updates searched employee's secret question
    public void updateSecretQ(ActionEvent clickedButton) throws IOException, SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (secretQ.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else {
                updateDetailsModel.updateSecretQ(enteredUsername.getText(), secretQ.getText());
                errorMessage.setText("Updated!");
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }
    }

    //purpose: updates searched employee's secret answer
    public void updateSecretA(ActionEvent clickedButton) throws IOException, SQLException {
        if (updateDetailsModel.usernameExists(enteredUsername.getText())) {
            if (secretA.getText().trim().isEmpty()) {
                errorMessage.setText("Nothing entered!");
            } else {
                updateDetailsModel.updateSecretA(enteredUsername.getText(), secretA.getText());
                errorMessage.setText("Updated!");
            }
        } else {
            errorMessage.setText("Enter an existing username!");
        }
    }

}
