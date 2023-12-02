package sgu.ltudm.songssingersproject;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sgu.ltudm.songssingersproject.Helpers.MyAlert;
import sgu.ltudm.songssingersproject.models.Client;
import sgu.ltudm.songssingersproject.models.Model;

public class App extends Application {

    public static void main(String[] args) {
        try {
            launch();
        } catch (Exception e) {
            MyAlert alert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }


    @Override
    public void start(Stage primaryStage) {
        try {
            Client.connectServer();

            Model.getInstance().getView().showAppWindow();
            Model.getInstance().getView().getAppSelectedMenuItem().set("Home");
        } catch (Exception e) {
            MyAlert alert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }
}