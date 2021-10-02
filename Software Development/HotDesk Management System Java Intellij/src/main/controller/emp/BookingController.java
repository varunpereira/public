package main.controller.emp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import main.model.emp.BookingModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import main.model.shared.LoginModel;

import static java.sql.Date.valueOf;

//purpose: application logic for making a booking via employee account
public class BookingController implements Initializable {
    @FXML
    private Label isConnected;
    private Rectangle[] deskList = new Rectangle[8];
    @FXML
    private Rectangle null_null_null_1;
    @FXML
    private Rectangle null_null_null_2;
    @FXML
    private Rectangle null_null_null_3;
    @FXML
    private Rectangle null_null_null_4;
    @FXML
    private Rectangle null_null_null_5;
    @FXML
    private Rectangle null_null_null_6;
    @FXML
    private Rectangle null_null_null_7;
    @FXML
    private Rectangle null_null_null_8;
    @FXML
    private DatePicker calendar;
    @FXML
    private Label dateError;
    @FXML
    private Label deskBookingError;
    private boolean deskBooked;
    private String deskColour;
    private String deskDate;
    private int deskLocation;
    private BookingModel bookingModel = new BookingModel();

    //purpose: initialise member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (bookingModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
        deskBooked = false;
    }

    //purpose: loads make a booking page
    public void BookingScene(ActionEvent clickedButton) throws IOException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/emp/BookingView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: response when you enter the chosen date
    public void clickDate(ActionEvent clickedButton) throws IOException, ParseException {
        if (calendar.getValue() != null) {
            //earliest you can book is tomorrow, from current date.
            Date todaysDate = new Date();
            Date enteredDate = valueOf(calendar.getValue());
            Boolean enteredDateAfterToday = (todaysDate.before(enteredDate));
            if (enteredDateAfterToday) {
                dateError.setText("VALID date!");
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String inputtedDate = dateFormat.format(valueOf(calendar.getValue()));
                reassignDesk();
                for (int i = 0; i < deskList.length; i += 1) {
                    try {
                        String color = "null";
                        String username = "null";
                        if (bookingModel.checkDeskColour(inputtedDate, (i + 1), "red")) {
                            color = "red";
                            deskList[i].setFill(Color.RED);
                            username = bookingModel.getUsername();
                        } else if (bookingModel.checkDeskColour(inputtedDate, (i + 1), "blue")) {
                            color = "blue";
                            deskList[i].setFill(Color.BLUE);
                            username = bookingModel.getUsername();
                        } else if (bookingModel.checkDeskColour(inputtedDate, (i + 1), "orange")) {
                            color = "orange";
                            deskList[i].setFill(Color.ORANGE);
                            username = "null";
                        } else {
                            color = "green";
                            deskList[i].setFill(Color.GREEN);
                            username = "null";
                        }
                        deskList[i].setId(color + "_" + username + "_" + inputtedDate + "_" + (i + 1));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                dateError.setText("Earliest you can book is tomorrow!");
            }
        } else {
            dateError.setText("Enter in a date!");
        }
    }

    //purpose: response when you click a desk
    public void clickDesk(ActionEvent clickedButton) throws IOException, SQLException {
        reassignDesk();
        Button btn = (Button) clickedButton.getSource();
        int buttonId = Integer.parseInt(btn.getId());
        String deskId = deskList[buttonId - 1].getId();
        String[] array = deskId.split("_", 4);
        deskColour = array[0];
        deskDate = array[2];
        deskLocation = Integer.parseInt(array[3]);
        if (deskList[deskLocation - 1].getFill() == Color.RED) {
            deskBooked = false;
            deskBookingError.setText("DESK ALREADY BOOKED!");
        } else if (deskList[deskLocation - 1].getFill() == Color.ORANGE) {
            deskBooked = false;
            deskBookingError.setText("COVID RESTRICTIONS!");
        } else if (deskList[deskLocation - 1].getFill() == Color.BLUE) {
            deskBooked = false;
            deskBookingError.setText("DESK ALREADY REQUESTED!");
        } else if (deskList[deskLocation - 1].getFill() == Color.GREEN && deskBooked == false) {
            optionGreenDeskAndBookingNotDone();
        } else if (deskList[deskLocation - 1].getFill() == Color.GREEN && deskBooked == true) {
            deskBooked = true;
            deskBookingError.setText("CAN'T BOOK MORE THAN 1 DESK!");
        } else if (deskList[deskLocation - 1].getFill() == Color.YELLOW && deskBooked == true) {
            deskBooked = false;
            deskList[deskLocation - 1].setFill(Color.GREEN);
            deskBookingError.setText("");
        } else {
            deskBooked = false;
            dateError.setText("PLEASE CHOOSE A DATE!");
        }
    }

    //purpose: the only allowed desk option for booking (in its own method due to larger size)
    public void optionGreenDeskAndBookingNotDone() throws SQLException {
        bookingModel.findUsername();
        String usernameLoggedIn = bookingModel.getUsername();
        String belowDeskUsername = "null";
        String aboveDeskUsername = "null";
        if (deskLocation == 1 || deskLocation == 5) {
            String[] belowDesk = deskList[(deskLocation - 1) + 1].getId().split("_", 4);
            belowDeskUsername = belowDesk[1];
        } else if (deskLocation == 4 || deskLocation == 8) {
            String[] aboveDesk = deskList[(deskLocation - 1) - 1].getId().split("_", 4);
            aboveDeskUsername = aboveDesk[1];
        } else {
            String[] belowDesk = deskList[(deskLocation - 1) + 1].getId().split("_", 4);
            belowDeskUsername = belowDesk[1];
            String[] aboveDesk = deskList[(deskLocation - 1) - 1].getId().split("_", 4);
            aboveDeskUsername = aboveDesk[1];
        }
        //check if sat next to employees before
        boolean alreadySatNextToAdjacentDesk = false;
        for (int i = 0; i < deskList.length; i += 1) {
            //check if desk location IS in Desks table
            if (bookingModel.loggedInUsernameDeskLocationExists(usernameLoggedIn, (i + 1))) {
                String adjacentDeskDate = bookingModel.getLoggedInUsernamesDate();
                if (bookingModel.locationDateVisitedBefore(belowDeskUsername, ((i + 1) + 1), adjacentDeskDate)) {
                    alreadySatNextToAdjacentDesk = true;
                }
                if (bookingModel.locationDateVisitedBefore(aboveDeskUsername, ((i + 1) + 1), adjacentDeskDate)) {
                    alreadySatNextToAdjacentDesk = true;
                }
                if (bookingModel.locationDateVisitedBefore(belowDeskUsername, ((i + 1) - 1), adjacentDeskDate)) {
                    alreadySatNextToAdjacentDesk = true;
                }
                if (bookingModel.locationDateVisitedBefore(aboveDeskUsername, ((i + 1) - 1), adjacentDeskDate)) {
                    alreadySatNextToAdjacentDesk = true;
                }
            }
        }
        //BONUS MARKS: check if already sat at desk location before
        if (bookingModel.locationVisitedBefore(usernameLoggedIn, deskLocation)) {
            deskBooked = false;
            deskBookingError.setText("SEAT LOCATION BOOKED BEFORE, TRY A DIFFERENT LOCATION.");
        }
        //BONUS MARKS: check if already sat next to adjacent employee before
        else if (alreadySatNextToAdjacentDesk == true) {
            deskBookingError.setText("ALREADY SAT NEXT TO PERSON BEFORE!");
        } else {
            deskBooked = true;
            deskList[deskLocation - 1].setFill(Color.YELLOW);
            deskBookingError.setText("");
        }
    }

    //purpose: response when employee confirms desk selection
    public void deskBooked(ActionEvent clickedButton) throws IOException, SQLException {
        reassignDesk();
        if (deskBooked == true) {
            deskBookingError.setText("SUCCESSFULLY REQUESTED DESK!");
            String deskColorRequest = "blue";
            bookingModel.findUsername();
            String username = bookingModel.getUsername();
            bookingModel.resetLatestBooking(username);
            bookingModel.bookingConfirmed(username, deskColorRequest, deskDate, deskLocation);
            EmpHomeController empHomeController = new EmpHomeController();
            empHomeController.EmpHomeScene(clickedButton);
        } else if (deskBooked == false) {
            deskBookingError.setText("PLEASE CHOOSE A DESK!");
        }
    }

    //purpose: reassigns desks from fxml file to this java class
    public void reassignDesk() {
        deskList[0] = null_null_null_1;
        deskList[1] = null_null_null_2;
        deskList[2] = null_null_null_3;
        deskList[3] = null_null_null_4;
        deskList[4] = null_null_null_5;
        deskList[5] = null_null_null_6;
        deskList[6] = null_null_null_7;
        deskList[7] = null_null_null_8;
    }

    //purpose: button that takes you back to employee home page
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

