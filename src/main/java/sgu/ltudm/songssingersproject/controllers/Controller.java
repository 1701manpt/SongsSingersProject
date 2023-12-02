package sgu.ltudm.songssingersproject.controllers;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import sgu.ltudm.songssingersproject.models.Model;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public BorderPane app_view;
    public AnchorPane app_content;

    public void setView(Parent view) {
        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);
        app_content.getChildren().clear();
        app_content.getChildren().add(view);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Parent homeView = Model.getInstance().getView().getHomeView();
        Parent singerView = Model.getInstance().getView().getSingerView();
        Parent songView = Model.getInstance().getView().getSongView();

        Model.getInstance().getView().getAppSelectedMenuItem().addListener((observableValue, s, t1) -> {
            switch (t1) {
                case "Home" -> {
                    setView(homeView);
                }
                case "Song" -> {
                    setView(songView);
                }
                case "Singer" -> {
                    setView(singerView);
                }
            }
        });
    }
}
