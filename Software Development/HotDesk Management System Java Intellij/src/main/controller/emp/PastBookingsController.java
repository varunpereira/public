package main.controller.emp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.model.emp.PastBookingsModel;
import javafx.scene.control.Label;
import main.model.shared.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//purpose: application logic for booking history of employee
public class PastBookingsController implements Initializable {
    @FXML
    private Label list;
    private boolean alreadyShown = false;
    private PastBookingsModel pastBookingsModel = new PastBookingsModel();

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    //purpose: loads employee booking history page
    public void PastBookingsScene(ActionEvent clickedButton) throws IOException, SQLException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/emp/PastBookingsView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: hyperlink that displays your booking history
    public void showPastBookings(ActionEvent clickedButton) throws IOException, SQLException {
        try {
            pastBookingsModel.findUsername();
            if (pastBookingsModel.findPastBookings(pastBookingsModel.getUsername())) {
                if (alreadyShown == false) {
                    alreadyShown = true;
                    int numPastBookings = pastBookingsModel.getLocation().size();
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < numPastBookings; i += 1) {
                        builder.append("Location: " + pastBookingsModel.getLocation().get(i) + ", Date: " + pastBookingsModel.getDate().get(i) + "\n");
                    }
                    String result = builder.toString();
                    list.setText(result);
                }
            } else {
                list.setText("No past bookings exist. Try and make a booking.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //purpose: button that goes to employee home page
    public void goHome(ActionEvent clickedButton) throws IOException, SQLException {
        EmpHomeController empHomeController = new EmpHomeController();
        empHomeController.EmpHomeScene(clickedButton);
    }

    //purpose: button that log you out of your account
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
