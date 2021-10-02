package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.admin.AdminHomeModel;
import main.model.emp.EmpHomeModel;
import main.model.shared.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//purpose: application logic for admin home page
public class AdminHomeController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private Label errorMessage;
    @FXML
    private Label downloadMessage;
    @FXML
    private Label pageHeading;

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AdminHomeModel adminHomeModel = new AdminHomeModel();
        if (adminHomeModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
        try {
            EmpHomeModel empHomeModel = new EmpHomeModel();
            empHomeModel.findUsername();
            pageHeading.setText("Welcome " + empHomeModel.getUsername() + "!\nHome Page");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        downloadMessage.setText("Before Downloading, change file directory in:\n'AdminHomeModel.java'");
        errorMessage.setText("");
    }

    //purpose: load admin home page
    public void AdminHomeScene(ActionEvent clickedButton) throws IOException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/admin/AdminHomeView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: button which goes to employee booking requests page
    public void goToBookingRequests(ActionEvent clickedButton) throws IOException, SQLException {
        BookingRequestsController bookingRequestsController = new BookingRequestsController();
        bookingRequestsController.BookingRequestsScene(clickedButton);
    }

    //purpose: button which goes to CRUD pages of employee
    public void goToManageEmps(ActionEvent clickedButton) throws IOException, SQLException {
        UpdateDeleteEmpController updateDeleteEmpController = new UpdateDeleteEmpController();
        updateDeleteEmpController.UpdateDeleteEmpScene(clickedButton);
    }

    //purpose: button which goes to CRUD pages of admins
    public void goToManageAdmins(ActionEvent clickedButton) throws IOException, SQLException {
        UpdateDeleteAdminController updateDeleteAdminController = new UpdateDeleteAdminController();
        updateDeleteAdminController.UpdateDeleteAdminScene(clickedButton);
    }

    //purpose: button which downloads employees' booking history
    public void downloadBookingInfo(ActionEvent clickedButton) throws IOException, SQLException {
        AdminHomeModel adminHomeModel = new AdminHomeModel();
        adminHomeModel.downloadBookingInfo();
        errorMessage.setText("'BookingInfo.csv' downloaded successfully.");
    }

    //purpose: button which downloads all employees' details
    public void downloadEmployeeInfo(ActionEvent clickedButton) throws IOException, SQLException {
        AdminHomeModel adminHomeModel = new AdminHomeModel();
        adminHomeModel.downloadEmployeeInfo();
        errorMessage.setText("'EmployeeInfo.csv' downloaded successfully.");
    }

    //purpose: button which logs out admin to login page
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
