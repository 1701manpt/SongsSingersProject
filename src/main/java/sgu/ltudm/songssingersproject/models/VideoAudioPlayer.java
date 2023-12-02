package sgu.ltudm.songssingersproject.models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class VideoAudioPlayer {

    public static MediaPlayer mediaPlayer;

    public VideoAudioPlayer(String url) {
        Media media = new Media(url);
        mediaPlayer = new MediaPlayer(media);
    }

    public static void clear() {
        if (mediaPlayer != null) {
            // Dừng phát nhạc
            mediaPlayer.stop();

            // Giải phóng tài nguyên của MediaPlayer
            mediaPlayer.dispose();

            // Đặt mediaPlayer về null để không giữ tham chiếu đến đối tượng đã bị giải phóng
            mediaPlayer = null;
        }
    }

    public void playMedia() {
        mediaPlayer.play();
    }

    public void pauseMedia() {
        mediaPlayer.pause();
    }

    public void stopMedia() {
        mediaPlayer.stop();
    }
}


