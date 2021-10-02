package main.appsetup;

import javafx.application.Application;
import javafx.stage.Stage;
import main.controller.shared.LoginController;

public class Main extends Application {

    //purpose: load first page, login page
    @Override
    public void start(Stage primaryStage) throws Exception{
        LoginController loginWindow = new LoginController();
        loginWindow.LoginScene();
    }

    //purpose: start program
    public static void main(String[] args) {
        launch(args);
    }
}
