package main.controller.shared;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import main.controller.admin.AdminHomeController;
import main.controller.emp.EmpHomeController;
import main.model.emp.BookingModel;
import main.model.emp.EmpHomeModel;
import main.model.shared.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.sql.Date.valueOf;

//purpose: application logic for login page
public class LoginController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private Label loginError;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    private LoginModel loginModel = new LoginModel();
    private EmpHomeController empHomeController = new EmpHomeController();
    private RegisterController registerController = new RegisterController();
    private SecretAnswerController secretAnswerController = new SecretAnswerController();

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
    }

    //purpose: loads the login page
    public void LoginScene() throws IOException, SQLException {
        try {
            Stage window = new Stage();
            Parent loadView = FXMLLoader.load(getClass().getResource("../../view/shared/LoginView.fxml"));
            Scene scene = new Scene(loadView);
            window.setScene(scene);
            window.setTitle("HotDesk+");
            window.show();
            loginModel.allLoggedOff();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //purpose: loads the login page
    public void LoginScene(ActionEvent clickedButton) throws IOException, SQLException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/shared/LoginView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: checks if user login info is correct else dont login
    public void checkAccountExists(ActionEvent clickedButton) throws IOException {
        try {
            if (loginModel.accountExists(txtUsername.getText(), txtPassword.getText())) {
                loginError.setText("Logged in successfully!");
                loginModel.isLoggedIn(txtUsername.getText());
                try {
                    if (loginModel.isEmp(txtUsername.getText())) {
                        //goes to Employee side of program
                        try {
                            //removes employee's current desk if its before today
                            //should do this for all employees not just one that logs in
                            EmpHomeModel empHomeModel = new EmpHomeModel();
                            if (empHomeModel.checkCurrentDeskDatePassed(txtUsername.getText())) {
                                Date todaysDate = new Date();
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(todaysDate);
                                cal.add(Calendar.DATE, -1);
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                String yesterdayDate = dateFormat.format(cal.getTime());
                                Date modifiedDate = dateFormat.parse(yesterdayDate);
                                Date currentDeskDate = dateFormat.parse(empHomeModel.getDate());
                                Boolean deskDateIsNotOver = (currentDeskDate.after(modifiedDate));
                                if (deskDateIsNotOver == false) {
                                    BookingModel bookingModel = new BookingModel();
                                    bookingModel.resetLatestBooking(txtUsername.getText());
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        empHomeController.EmpHomeScene(clickedButton);
                    } else {
                        //goes to Admin side of program
                        AdminHomeController adminHomeController = new AdminHomeController();
                        adminHomeController.AdminHomeScene(clickedButton);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                loginError.setText("Wrong username or password.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //purpose: hyperlink that makes you answer a secret question, then reset password
    public void forgotPassword(ActionEvent clickedButton) throws IOException {
        secretAnswerController.SecretAnswerScene(clickedButton);
    }

    //purpose: button that allows you to register an account
    public void register(ActionEvent clickedButton) throws IOException {
        registerController.RegisterScene(clickedButton);
    }

}
