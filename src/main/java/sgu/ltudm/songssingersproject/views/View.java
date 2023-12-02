package sgu.ltudm.songssingersproject.views;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class View {
    private final StringProperty appSelectedMenuItem;
    private AnchorPane homeView;
    private AnchorPane singerView;
    private AnchorPane songView;

    public View() {
        this.appSelectedMenuItem = new SimpleStringProperty("");
    }

    public StringProperty getAppSelectedMenuItem() {
        return appSelectedMenuItem;
    }

    public void showAppWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sgu/ltudm/songssingersproject/fxmls/app-view.fxml"));
        createStage(loader);
    }

    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
//            Panel panel = new Panel("Songs and Singers App");
//            panel.getStyleClass().add("panel-primary");
//            panel.setBody(loader.load());
            scene = new Scene(loader.load());
//            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Stage primaryStage = new Stage();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Ứng dụng tìm kiếm bài hát, ca sĩ,... - Nhóm 3 - Lập trình ứng dụng mạng");

        // Đặt biểu tượng ứng dụng
        Image icon = new Image(getClass().getResourceAsStream("/sgu/ltudm/songssingersproject/icons/icon-app-Nam.png"));
        primaryStage.getIcons().add(icon);

        primaryStage.setMaximized(true);
//        primaryStage.setMinWidth(1024);
//        primaryStage.setMinHeight(768);

        primaryStage.show();
    }

    public AnchorPane getHomeView() {
        if (homeView == null) {
            try {
                homeView = new FXMLLoader(getClass().getResource("/sgu/ltudm/songssingersproject/fxmls/home-view.fxml")).load();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return homeView;
    }

    public AnchorPane getSingerView() {
        if (singerView == null) {
            try {
                singerView = new FXMLLoader(getClass().getResource("/sgu/ltudm/songssingersproject/fxmls/singer-view.fxml")).load();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return singerView;
    }

    public AnchorPane getSongView() {
        if (songView == null) {
            try {
                songView = new FXMLLoader(getClass().getResource("/sgu/ltudm/songssingersproject/fxmls/song-view.fxml")).load();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return songView;
    }
}
