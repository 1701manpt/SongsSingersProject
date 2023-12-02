package sgu.ltudm.songssingersproject.Helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MyAlert {
    private final String content;
    private final AlertType type;

    public MyAlert(String content, AlertType type) {
        this.content = content;
        this.type = type;
    }

    public void show() {
        Alert alert = new Alert(type);
        alert.setTitle("Thông báo");
        alert.setHeaderText(content);
//        alert.setContentText(content);

        alert.showAndWait();
    }
}
