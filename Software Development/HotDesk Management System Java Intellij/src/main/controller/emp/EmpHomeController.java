package main.controller.emp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.model.emp.BookingModel;
import main.model.emp.EmpHomeModel;
import main.model.shared.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

//purpose: application logic for employee home page
public class EmpHomeController implements Initializable {
    @FXML
    private Label isConnected;
    @FXML
    private Label currentBooking;
    @FXML
    private Hyperlink cancel;
    @FXML
    private Label errorMessage;
    @FXML
    private Label pageHeading;
    private EmpHomeModel empHomeModel = new EmpHomeModel();
    private BookingModel bookingModel = new BookingModel();
    private BookingController bookingController = new BookingController();
    private PastBookingsController pastBookingsController = new PastBookingsController();
    private UpdateDetailsController updateDetailsController = new UpdateDetailsController();

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bookingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
        try {
            empHomeModel.findUsername();
            pageHeading.setText("Welcome " + empHomeModel.getUsername() + "!\nHome Page");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        errorMessage.setText("");
    }

    //purpose: loads employee home page
    public void EmpHomeScene(ActionEvent clickedButton) throws IOException, SQLException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/emp/EmpHomeView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: goes to make a booking page if dont have a desk booked already
    public void goToMakeBooking(ActionEvent clickedButton) throws IOException, SQLException {
        try {
            empHomeModel.findUsername();
            if (empHomeModel.findLatestConfirmedBooking(empHomeModel.getUsername())) {
                errorMessage.setText("You already have a CONFIRMED current booking!");
            } else if (empHomeModel.findRequestedBooking(empHomeModel.getUsername())) {
                errorMessage.setText("You already have a REQUESTED current booking!");
            } else {
                bookingController.BookingScene(clickedButton);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //purpose: button that goes to a page to allow you to view your past booking
    public void goToViewPastBookings(ActionEvent clickedButton) throws IOException, SQLException {
        pastBookingsController.PastBookingsScene(clickedButton);
    }

    //purpose: button which goes to to a page to allow you to update your details
    public void goToUpdateDetails(ActionEvent clickedButton) throws IOException, SQLException {
        updateDetailsController.UpdateDetailsScene(clickedButton);
    }

    //purpose: hyperlink that shows your current booking if it exists
    public void showCurrentBooking(ActionEvent clickedButton) throws IOException, SQLException {
        try {
            empHomeModel.findUsername();
            if (empHomeModel.findLatestConfirmedBooking(empHomeModel.getUsername())) {
                cancel.setText("Cancel");
                currentBooking.setText("CONFIRMED: Location: " + empHomeModel.getLocation() + ", Date: " + empHomeModel.getDate());
            } else if (empHomeModel.findRequestedBooking(empHomeModel.getUsername())) {
                cancel.setText("Cancel");
                currentBooking.setText("REQUESTED: Location: " + empHomeModel.getLocation() + ", Date: " + empHomeModel.getDate());
            } else {
                cancel.setText("");
                currentBooking.setText("No desk currently booked.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //purpose: hyperlink that allows you to cancel current booking if it exists
    public void cancelCurrentBooking(ActionEvent clickedButton) throws IOException, SQLException {
        try {
            empHomeModel.findUsername();
            if (empHomeModel.findLatestConfirmedBooking(empHomeModel.getUsername()) || empHomeModel.findRequestedBooking(empHomeModel.getUsername())) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                dateFormat.setLenient(false);
                Date currentDateNotString = new Date();
                String currentDateString = dateFormat.format(currentDateNotString);
                Date todaysDate = new SimpleDateFormat("dd-MM-yyyy").parse(currentDateString);
                String bookingDateString = empHomeModel.getDate();
                Date bookingDate = new SimpleDateFormat("dd-MM-yyyy").parse(bookingDateString);
                Calendar cal = Calendar.getInstance();
                cal.setTime(bookingDate);
                cal.add(Calendar.DATE, -2);
                String lastDayToCancel = dateFormat.format(cal.getTime());
                Date bookingDateModified = new SimpleDateFormat("dd-MM-yy").parse(lastDayToCancel);
                //latest is booking date minus 3 days max (coz if you do at night then its closer to 2 days).
                Boolean deskDateIsOver = (todaysDate.before(bookingDateModified));
                if (deskDateIsOver == true) {
                    empHomeModel.deleteCurrentBooking(empHomeModel.getUsername());
                    currentBooking.setText("Booking deleted. No desk currently booked.");
                    errorMessage.setText("");
                } else {
                    errorMessage.setText("Sorry, can not cancel within 48 hours of booking day.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
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
