package sgu.ltudm.songssingersproject.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import sgu.ltudm.songssingersproject.Helpers.MyAlert;
import sgu.ltudm.songssingersproject.models.Client;
import sgu.ltudm.songssingersproject.models.SongClient;
import sgu.ltudm.songssingersproject.models.SongModel;
import sgu.ltudm.songssingersproject.models.VideoAudioPlayer;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class SongController implements Initializable {
    public TextField SongTitle_TextField;
    public Button Search_Button;
    public Text Result_Text;
    public SongClient songClient;
    public ScrollPane ScrollPane;
    public Pane VideoLayout;
    public ProgressIndicator progressIndicator;
    public VBox vbox;
    public Button PlayButton;
    public Button PauseButton;
    public Button StopButton;
    public Slider VolumeSlider;
    public MediaView VideoMediaView;
    public Button PlayAudioButton;
    public Button PlayVideoButton;
    public VBox MediaPlayerLayout;
    public Text SongTitle;
    private VideoAudioPlayer media;
    private SongModel song;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            PlayButton.setOnAction(e -> play());
            PlayVideoButton.setOnAction(e -> playVideo());
            PlayAudioButton.setOnAction(e -> playAudio());
            PauseButton.setOnAction(e -> pause());
            StopButton.setOnAction(e -> stop());

            // Tạo một DoubleBinding giữa wrappingWidth và width của viewportBounds
            DoubleBinding wrappingWidthBinding = Bindings.createDoubleBinding(() -> ScrollPane.viewportBoundsProperty().get().getWidth(), ScrollPane.viewportBoundsProperty());
            // Thiết lập wrappingWidth cho Text
            Result_Text.wrappingWidthProperty().bind(wrappingWidthBinding);
            vbox.minHeightProperty().bind(ScrollPane.heightProperty().multiply(2.0 / 4.0));
            VideoMediaView.fitWidthProperty().bind(wrappingWidthBinding);

            Search_Button.setOnAction(a -> {
                clearResultPrev();
                String keyword = SongTitle_TextField.getText();
                taskSearch(keyword);
            });
        } catch (Exception e) {
            MyAlert alert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }

    private void clearResultPrev() {
        Result_Text.setText("");
        VideoAudioPlayer.clear();
        MediaPlayerLayout.setVisible(false);
    }

    public void taskSearch(String str) {
        songClient = new SongClient(Client.in, Client.out);

        // Thực hiện công việc mạng trong một Task
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {

                songClient.requestSong(str);

                return null;
            }
        };

        // Xử lý sự kiện khi Task hoàn thành
        task.setOnSucceeded(event -> {
            // Cập nhật giao diện người dùng ở đây (nếu cần)
            progressIndicator.setVisible(false);

            if (songClient.json.split(":")[0].equals("error")) {
                MyAlert alert = new MyAlert(songClient.json.split(":")[1], Alert.AlertType.ERROR);
                alert.show();
                return;
            }

            searchSong();
        });

        // Bind ProgressIndicator với tiến trình của Task
        progressIndicator.progressProperty().bind(task.progressProperty());

        // Hiển thị ProgressIndicator và bắt đầu công việc khi scene được hiển thị
        progressIndicator.setVisible(true);

        // Bắt đầu thực hiện Task trong một luồng riêng
        new Thread(task).start();
    }

    public void searchSong() {
        try {
            SongModel songModel = new SongModel();
            song = songModel.toObject(songClient.json);

            LinkedList<String> list = new LinkedList<>();
            list.add("Tên bài hát: " + song.getTitle());
            list.add("Nhạc sĩ sáng tác: " + song.getComposer());
            list.add("Lời bài hát: \n" + song.getLyric());

            Result_Text.setText(String.join("\n\n", list));

            SongTitle.setText("Bài hát: " + song.getTitle() + " - Ca sĩ: " + song.getArtists());

            playAudio();
        } catch (Exception e) {
            MyAlert alert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }

    private void playVideo() {
        VideoAudioPlayer.clear();
        MediaPlayerLayout.setVisible(true);
        media = new VideoAudioPlayer(song.getUrlVideo());
        VideoMediaView.setMediaPlayer(VideoAudioPlayer.mediaPlayer);
        play();
        // Liên kết thanh trượt âm lượng với âm lượng của MediaPlayer
        VideoAudioPlayer.mediaPlayer.volumeProperty().bind(VolumeSlider.valueProperty());
    }

    private void playAudio() {
        VideoAudioPlayer.clear();
        MediaPlayerLayout.setVisible(true);
        media = new VideoAudioPlayer(song.getUrlAudio());
        VideoMediaView.setMediaPlayer(VideoAudioPlayer.mediaPlayer);
        play();
        // Liên kết thanh trượt âm lượng với âm lượng của MediaPlayer
        VideoAudioPlayer.mediaPlayer.volumeProperty().bind(VolumeSlider.valueProperty());
    }

    private void play() {
        media.playMedia();
    }

    private void pause() {
        media.pauseMedia();
    }

    private void stop() {
        media.stopMedia();
    }

    private void createMediaPlayView() {
        VideoMediaView = new MediaView();

    }
}
