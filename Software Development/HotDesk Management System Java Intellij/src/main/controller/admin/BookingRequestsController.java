package main.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.model.admin.BookingRequestsModel;
import main.model.shared.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

//purpose: application logic for managing booking requests page
public class BookingRequestsController implements Initializable {
    public BookingRequestsModel bookingRequestsModel = new BookingRequestsModel();
    public LoginModel loginModel = new LoginModel();
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
    private Label deskBookingError;
    private int deskBookedLocation;
    private String deskColor;
    private String deskUsername;
    private String deskDate;
    private int deskLocation;
    private int numRequestsConfirmed;
    private boolean isCovidCondition;
    private boolean isCovidLockdown;
    private String tomorrowRequestsDate;
    private String[] initialDeskColors = new String[8];
    private String[] clickeDeskUsernames = new String[8];
    private boolean isCovidFinalised;

    //purpose: initialises member variables
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loginModel.isDbConnected()) {
            isConnected.setText("Connected");
        } else {
            isConnected.setText("Not Connected");
        }
        deskList[0] = null_null_null_1;
        deskList[1] = null_null_null_2;
        deskList[2] = null_null_null_3;
        deskList[3] = null_null_null_4;
        deskList[4] = null_null_null_5;
        deskList[5] = null_null_null_6;
        deskList[6] = null_null_null_7;
        deskList[7] = null_null_null_8;
        setup();
    }

    //purpose: initialises member variables and sets up desk colours from db
    public void setup() {
        numRequestsConfirmed = 0;
        deskUsername = "";
        for (int i = 0; i < deskList.length; i += 1) {
            clickeDeskUsernames[i] = "null";
        }
        isCovidCondition = false;
        isCovidLockdown = false;
        isCovidFinalised = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
        Date currentDateNotString = new Date();
        String currentDateString = dateFormat.format(currentDateNotString);
        Date todaysDate = null;
        try {
            todaysDate = new SimpleDateFormat("dd-MM-yyyy").parse(currentDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(todaysDate);
        cal.add(Calendar.DATE, +1);
        this.tomorrowRequestsDate = dateFormat.format(cal.getTime());
        for (int i = 0; i < deskList.length; i += 1) {
            try {
                String color = "null";
                if (bookingRequestsModel.checkDeskColour(tomorrowRequestsDate, (i + 1), "red")) {
                    color = "red";
                    deskList[i].setFill(Color.RED);
                    initialDeskColors[i] = "red";
                } else if (bookingRequestsModel.checkDeskColour(tomorrowRequestsDate, (i + 1), "blue")) {
                    color = "blue";
                    deskList[i].setFill(Color.BLUE);
                    initialDeskColors[i] = "blue";
                } else if (bookingRequestsModel.checkDeskColour(tomorrowRequestsDate, (i + 1), "orange")) {
                    color = "orange";
                    deskList[i].setFill(Color.ORANGE);
                    initialDeskColors[i] = "orange";
                    isCovidFinalised = true;
                } else {
                    color = "green";
                    deskList[i].setFill(Color.GREEN);
                    initialDeskColors[i] = "green";
                }
                deskList[i].setId(color + "_null_" + tomorrowRequestsDate + "_" + (i + 1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //purpose: loads employee requests page which admin can confirm or reject
    public void BookingRequestsScene(ActionEvent clickedButton) throws IOException, SQLException {
        Stage window = (Stage) ((Node) clickedButton.getSource()).getScene().getWindow();
        Parent loadView = FXMLLoader.load(getClass().getResource("../../view/admin/BookingRequestsView.fxml"));
        Scene scene = new Scene(loadView);
        window.setScene(scene);
        window.show();
    }

    //purpose: reassigns fxml desks to member array
    public void assignDesk() {
        deskList[0] = null_null_null_1;
        deskList[1] = null_null_null_2;
        deskList[2] = null_null_null_3;
        deskList[3] = null_null_null_4;
        deskList[4] = null_null_null_5;
        deskList[5] = null_null_null_6;
        deskList[6] = null_null_null_7;
        deskList[7] = null_null_null_8;
    }

    //purpose: button which goes to admin home page
    public void goHome(ActionEvent clickedButton) throws IOException, SQLException {
        AdminHomeController adminHomeController = new AdminHomeController();
        adminHomeController.AdminHomeScene(clickedButton);
    }

    //purpose: response when admin clicks a desk
    public void clickDesk(ActionEvent clickedButton) throws IOException, SQLException {
        assignDesk();
        Button btn = (Button) clickedButton.getSource();
        int buttonId = Integer.parseInt(btn.getId());
        String deskId = deskList[buttonId - 1].getId();
        String[] array = deskId.split("_", 4);
        deskColor = array[0];
        deskUsername = array[1];
        deskDate = array[2];
        deskLocation = Integer.parseInt(array[3]);
        if (deskList[deskLocation - 1].getFill() == Color.RED) {
            numRequestsConfirmed = 0;
            deskBookingError.setText("REQUEST ALREADY CONFIRMED!");
        } else if (deskList[deskLocation - 1].getFill() == Color.ORANGE) {
            numRequestsConfirmed = 0;
            deskBookingError.setText("DESK IS NOT A REQUEST!");
        } else if (deskList[deskLocation - 1].getFill() == Color.GREEN) {
            numRequestsConfirmed = 0;
            deskBookingError.setText("DESK IS NOT A REQUEST!");
        } else if (deskList[deskLocation - 1].getFill() == Color.BLUE) {
            numRequestsConfirmed += 1;
            deskBookedLocation = deskLocation;
            deskList[deskLocation - 1].setFill(Color.YELLOW);
            deskBookingError.setText("");
            initialDeskColors[deskLocation - 1] = "yellow";
            clickeDeskUsernames[deskLocation - 1] = deskUsername;
        } else if (deskList[deskLocation - 1].getFill() == Color.YELLOW) {
            numRequestsConfirmed -= 1;
            deskList[deskLocation - 1].setFill(Color.BLUE);
            deskBookingError.setText("");
            clickeDeskUsernames[deskLocation - 1] = "null";
        }
    }

    //purpose: response when admin clicks confirm requests
    public void requestsConfirmed(ActionEvent clickedButton) throws IOException {
        assignDesk();
        if (numRequestsConfirmed > 0) {
            try {
                String deskColorConfirmed = "red";
                for (int i = 0; i < deskList.length; i += 1) {
                    if (clickeDeskUsernames[i] != "null") {
                        deskBookingError.setText("SUCCESSFULLY BOOKED!");
                        bookingRequestsModel.findUsername(this.tomorrowRequestsDate, (i + 1));
                        String clickedUsername = bookingRequestsModel.getUsername();
                        bookingRequestsModel.updateCurrentDeskToRed(clickedUsername, deskColorConfirmed);
                    }
                }
                AdminHomeController adminHomeController = new AdminHomeController();
                adminHomeController.AdminHomeScene(clickedButton);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (numRequestsConfirmed <= 0) {
            deskBookingError.setText("PLEASE CHOOSE DESKS TO CONFIRM!");
        }
    }

    //purpose: response when admin clicks reject requests
    public void requestsRejected(ActionEvent clickedButton) throws IOException {
        assignDesk();
        if (numRequestsConfirmed > 0) {
            try {
                for (int i = 0; i < deskList.length; i += 1) {
                    if (clickeDeskUsernames[i] != "null") {
                        deskBookingError.setText("SUCCESSFULLY REJECTED!");
                        bookingRequestsModel.deleteCurrentBooking(this.tomorrowRequestsDate, (i + 1));
                    }
                }
                AdminHomeController adminHomeController = new AdminHomeController();
                adminHomeController.AdminHomeScene(clickedButton);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (numRequestsConfirmed <= 0) {
            deskBookingError.setText("PLEASE CHOOSE DESKS TO REJECT!");
        }
    }

    //purpose: response when admin toggle covid lockdown button
    public void covidLockdown(ActionEvent clickedButton) throws IOException {
        if (isCovidFinalised) {
            deskBookingError.setText("Covid desks already finalised!");
        } else if (isCovidCondition) {
            deskBookingError.setText("Toggle Covid Condition OFF first!");
        } else {
            String color = "null";
            for (int i = 0; i < deskList.length; i += 1) {
                if (isCovidLockdown == true) {
                    if (initialDeskColors[i].equals("red")) {
                        deskList[i].setFill(Color.RED);
                        color = "red";
                    } else if (initialDeskColors[i].equals("green")) {
                        deskList[i].setFill(Color.GREEN);
                        color = "green";
                    } else if (initialDeskColors[i].equals("blue")) {
                        deskList[i].setFill(Color.BLUE);
                        color = "blue";
                    } else if (initialDeskColors[i].equals("yellow")) {
                        initialDeskColors[i] = "blue";
                        deskList[i].setFill(Color.BLUE);
                        color = "blue";
                    }
                } else if (isCovidLockdown == false) {
                    color = "orange";
                    deskList[i].setFill(Color.ORANGE);
                }
                deskList[i].setId(color + "_null_" + tomorrowRequestsDate + "_" + (i + 1));
            }
            if (isCovidLockdown == true) {
                isCovidLockdown = false;
            } else if (isCovidLockdown == false) {
                isCovidLockdown = true;
            }
        }
    }

    //response when admin clicks covid condition button
    public void covidCondition(ActionEvent clickedButton) throws IOException {
        if (isCovidFinalised) {
            deskBookingError.setText("Covid desks already finalised!");
        } else if (isCovidLockdown) {
            deskBookingError.setText("Toggle Covid Lockdown OFF first!");
        } else {
            for (int i = 0; i < deskList.length; i += 2) {
                String color = "null";
                if (isCovidCondition == true) {
                    if (initialDeskColors[i].equals("red")) {
                        deskList[i].setFill(Color.RED);
                        color = "red";
                    } else if (initialDeskColors[i].equals("orange")) {
                        deskList[i].setFill(Color.ORANGE);
                        color = "orange";
                    } else if (initialDeskColors[i].equals("green")) {
                        deskList[i].setFill(Color.GREEN);
                        color = "green";
                    } else if (initialDeskColors[i].equals("blue")) {
                        deskList[i].setFill(Color.BLUE);
                        color = "blue";
                    } else if (initialDeskColors[i].equals("yellow")) {
                        initialDeskColors[i] = "blue";
                        deskList[i].setFill(Color.BLUE);
                        color = "blue";
                    }
                } else if (isCovidCondition == false) {
                    color = "orange";
                    deskList[i].setFill(Color.ORANGE);
                }
                deskList[i].setId(color + "_null_" + tomorrowRequestsDate + "_" + (i + 1));
            }
            if (isCovidCondition == true) {
                isCovidCondition = false;
            } else if (isCovidCondition == false) {
                isCovidCondition = true;
            }
        }
    }

    //response when admin clicks finalise covid toggle button
    public void finaliseCovidToggle(ActionEvent clickedButton) throws IOException {
        try {
            if (isCovidFinalised) {
                deskBookingError.setText("Covid desks already finalised!");
            } else if (isCovidCondition) {
                for (int i = 0; i < deskList.length; i += 2) {
                    bookingRequestsModel.deleteCurrentBooking(this.tomorrowRequestsDate, (i + 1));
                    String covidDeskColour = "orange";
                    String covidUserUsername = "null";
                    bookingRequestsModel.covidDeskFinalised(covidUserUsername, this.tomorrowRequestsDate, (i + 1), covidDeskColour);
                }
                AdminHomeController adminHomeController = new AdminHomeController();
                adminHomeController.AdminHomeScene(clickedButton);
            } else if (isCovidLockdown) {
                for (int i = 0; i < deskList.length; i += 1) {
                    bookingRequestsModel.deleteCurrentBooking(this.tomorrowRequestsDate, (i + 1));
                    String covidDeskColour = "orange";
                    String covidUserUsername = "null";
                    bookingRequestsModel.covidDeskFinalised(covidUserUsername, this.tomorrowRequestsDate, (i + 1), covidDeskColour);
                }
                AdminHomeController adminHomeController = new AdminHomeController();
                adminHomeController.AdminHomeScene(clickedButton);
            } else {
                deskBookingError.setText("No Covid desks exist! Try toggling them on.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //purpose: logs out admin and loads login page
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
