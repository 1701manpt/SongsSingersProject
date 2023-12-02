package sgu.ltudm.songssingersproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML
    public Text homeData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void setHomeData(String msg) {
        System.out.println("hhihi");
        if (homeData != null) {
            homeData.setText(msg);
        } else {
            System.out.println("homeData is null");
        }
    }
}
