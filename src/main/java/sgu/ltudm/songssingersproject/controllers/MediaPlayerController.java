package sgu.ltudm.songssingersproject.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaView;
import sgu.ltudm.songssingersproject.models.VideoAudioPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class MediaPlayerController implements Initializable {
    @FXML
    public MediaView VideoMediaView;
    public Button PlayButton;
    public Button PauseButton;
    public Button StopButton;
    public Slider VolumeSlider;
    public VideoAudioPlayer media;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PlayButton.setOnAction(e -> play());
        PauseButton.setOnAction(e -> pause());
        StopButton.setOnAction(e -> stop());
    }

    public void setMediaPlayer(String url) {
        url = "https://rr4---sn-8qj-8j56.googlevideo.com/videoplayback?expire=1700734371&ei=Q9FeZcOCG-y1sfIPq_ep0Ac&ip=35.90.229.63&id=o-AOUevsXKfMK47xj8iUu8svocg-95UjzWZk3QxM9iQJHK&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&cnr=14&ratebypass=yes&dur=558.230&lmt=1699185765461440&fexp=24007246&c=ANDROID&txp=4532434&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Ccnr%2Cratebypass%2Cdur%2Clmt&sig=ANLwegAwRgIhAOy15iqlzmrtVUExTxGd_aGYOj2_-6E02r02D0xyevXIAiEAjJD7GqNjcynEoPbS60S7u1XtAQGi-MeQFAY0ejnGKqs%3D&redirect_counter=1&rm=sn-nx5zz7s&req_id=2cbaf32eb79da3ee&cms_redirect=yes&ipbypass=yes&mh=Lc&mip=113.185.87.118&mm=31&mn=sn-8qj-8j56&ms=au&mt=1700712314&mv=m&mvi=4&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AM8Gb2swRQIgFohSxP1U06XyI4A-rqzSDht2mRcKrmVsE7nACDgmagoCIQDSOVvfP-wnzzG887XGBKcslP8CsN9vuyKRLKhFyLOuyA%3D%3D";
        media = new VideoAudioPlayer(url);
        VideoMediaView.setMediaPlayer(VideoAudioPlayer.mediaPlayer);

//        media.mediaPlayer.setAutoPlay(true);

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

