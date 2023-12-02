package sgu.ltudm.songssingersproject.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import sgu.ltudm.songssingersproject.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class AppMenuController implements Initializable {
    public Button home_btn;
    public Button song_btn;
    public Button singer_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getView().getAppSelectedMenuItem().addListener((observableValue, s, t1) -> {
            switch (t1) {
                case "Home" -> {
                    setState("Home");
                }
                case "Song" -> {
                    setState("Song");
                }
                case "Singer" -> {
                    setState("Singer");
                }
            }
        });

        addListeners();
    }

    private void addListeners() {
        home_btn.setOnAction(event -> clickHomeBtn());
        song_btn.setOnAction(event -> clickSongBtn());
        singer_btn.setOnAction(event -> clickSingerBtn());
    }

    private void setState(String state) {
        switch (state) {
            case "Home" -> {
                home_btn.getStyleClass().add("active");
                song_btn.getStyleClass().remove("active");
                singer_btn.getStyleClass().remove("active");
            }
            case "Song" -> {
                song_btn.getStyleClass().add("active");
                home_btn.getStyleClass().remove("active");
                singer_btn.getStyleClass().remove("active");
            }
            case "Singer" -> {
                singer_btn.getStyleClass().add("active");
                song_btn.getStyleClass().remove("active");
                home_btn.getStyleClass().remove("active");
            }
        }
    }

    public void clickSongBtn() {
        Model.getInstance().getView().getAppSelectedMenuItem().set("Song");
    }

    public void clickSingerBtn() {
        Model.getInstance().getView().getAppSelectedMenuItem().set("Singer");
    }

    public void clickHomeBtn() {
        Model.getInstance().getView().getAppSelectedMenuItem().set("Home");
    }
}
