package sgu.ltudm.songssingersproject.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import sgu.ltudm.songssingersproject.Helpers.MyAlert;
import sgu.ltudm.songssingersproject.models.*;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class SingerController implements Initializable {
    public TextField SingerName_TextField;
    public Button Search_Button;
    public Text Result_Text;
    public ScrollPane ScrollPane;
    public ProgressIndicator progressIndicator;
    public VBox vbox;
    public MediaView VideoMediaView;
    public Button PlayButton;
    public Button PlayVideoButton;
    public Button PauseButton;
    public Button StopButton;
    public Slider VolumeSlider;
    public Button PlayAudioButton;
    public VBox MediaPlayerLayout;
    public Text SongTitle;
    private SingerClient singerClient;
    private SongClient songClient;
    private VideoAudioPlayer media;
    private GridPane gridSongs;
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

            singerClient = new SingerClient(Client.in, Client.out);

            Search_Button.setOnAction(a -> {
                // Thực hiện công việc mạng trong một Task
                clearResultPrev();

                Task<Void> task = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        String keyword = SingerName_TextField.getText();

                        singerClient.requestSinger(keyword);

                        return null;
                    }
                };

                // Bind ProgressIndicator với tiến trình của Task
                progressIndicator.progressProperty().bind(task.progressProperty());

                // Hiển thị ProgressIndicator và bắt đầu công việc khi scene được hiển thị
                progressIndicator.setVisible(true);

                // Xử lý sự kiện khi Task hoàn thành
                task.setOnSucceeded(event -> {
                    // Cập nhật giao diện người dùng ở đây (nếu cần)
                    progressIndicator.setVisible(false);

                    if (singerClient.json.split(":")[0].equals("error")) {
                        MyAlert alert = new MyAlert(singerClient.json.split(":")[1], Alert.AlertType.ERROR);
                        alert.show();
                        return;
                    }

                    searchSinger();
                });

                // Bắt đầu thực hiện Task trong một luồng riêng
                new Thread(task).start();
            });
        } catch (Exception e) {
            MyAlert alert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }

    private void clearResultPrev() {
        VideoAudioPlayer.clear();
        Result_Text.setText("");
        vbox.getChildren().remove(gridSongs);
        MediaPlayerLayout.setVisible(false);
    }

    public void searchSinger() {
        try {
            SingerModel singerModel = new SingerModel();
            SingerModel singer = singerModel.toObject(singerClient.json);

            LinkedList<String> list = new LinkedList<>();

            list.add("Tên ca sĩ: " + singer.getName());
            list.add("Ngày sinh: " + singer.getDateOfBirth());
            list.add("Nơi sinh: " + singer.getPlaceOfBirth());
            list.add("Tiểu sử và sự nghiệp: \n" + singer.getBiography());
            list.add("Danh sách bài hát: \n");

//            LinkedList<String> songsString = new LinkedList<>();
//            for (SongModel song: singer.getSongList()) {
//                songsString.add(song.getTitle() + "\n");
//            }
//            list.add(String.join("\n", songsString));

            Result_Text.setText(String.join("\n\n", list));

            gridSongs = createGridSongList(singer.getSongList());
            vbox.getChildren().add(1, gridSongs);
        } catch (Exception e) {
            MyAlert alert = new MyAlert(e.getMessage(), Alert.AlertType.ERROR);
            alert.show();
        }
    }

    private GridPane createGridSongList(LinkedList<SongModel> songList) {
        DoubleBinding wrappingWidthBinding = Bindings.createDoubleBinding(() -> ScrollPane.viewportBoundsProperty().get().getWidth(), ScrollPane.viewportBoundsProperty());

        // Tạo một GridPane
        GridPane gridPane = new GridPane();
        gridPane.prefWidthProperty().bind(wrappingWidthBinding);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        // Thêm nút vào GridPane
        int numCols = 3;
        int numRows = Math.round(songList.size() / numCols);

        // Use two indices to traverse the grid
        int row = 0;
        int col = 0;

        for (SongModel song : songList) {
            Button button = new Button(song.getTitle());
            button.setOnAction(event -> {
                clickSong(song.getTitle());
            });
            gridPane.add(button, col, row);

            col++;
            if (col == numCols) {
                col = 0;
                row++;
            }
        }

        return gridPane;
    }

    private void clickSong(String str) {

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

            prepare();
        });

        // Bind ProgressIndicator với tiến trình của Task
        progressIndicator.progressProperty().bind(task.progressProperty());

        // Hiển thị ProgressIndicator và bắt đầu công việc khi scene được hiển thị
        progressIndicator.setVisible(true);

        // Bắt đầu thực hiện Task trong một luồng riêng
        new Thread(task).start();
    }

    private void prepare() {
        try {
            SongModel songModel = new SongModel();
            song = songModel.toObject(songClient.json);

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
}
