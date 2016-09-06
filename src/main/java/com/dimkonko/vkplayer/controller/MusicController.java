package com.dimkonko.vkplayer.controller;

import com.dimkonko.jvkapi.model.VkToken;
import com.dimkonko.vkplayer.App;
import com.dimkonko.vkplayer.api.VkClient;
import com.dimkonko.vkplayer.exception.AccessTokenFailedException;
import com.dimkonko.vkplayer.exception.BadRequestException;
import com.dimkonko.vkplayer.model.json.AudioModel;
import com.dimkonko.vkplayer.player.AudioPlayer;
import com.dimkonko.vkplayer.service.TImeUtils;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javazoom.jl.decoder.JavaLayerException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

public class MusicController implements Initializable {
    private static final String TIME_PATTERN = "%s/%s";

    @FXML private Label titleLabel;
    @FXML private Button playButton;
    @FXML public Slider timeSlider;
    @FXML private Label timeLabel;
    @FXML public ListView<AudioModel> playlistView;
    @FXML public MediaView mView;

    private final VkClient vkClient = VkClient.getInstance();
    private final AudioPlayer audioPlayer = new AudioPlayer();
//    private final AudioLoader audioLoader = new AudioLoader("Downloads/");

    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setText("Play");
        playButton.setDisable(true);
        setTimeLabelText(getTimeText(0), getTimeText(0));

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(javafx.beans.Observable observable) {
                if (timeSlider.isValueChanging()) {
                    audioPlayer.seek(new Duration(timeSlider.getValue() * 1000));
                }
            }
        });

        loadAudio();
    }

    private void loadAudio() {
        //http://cs1-19v4.vk-cdn.net/p34/a518c388da82a6.mp3?extra=Ob0WJ_mMi3x5XKCAqyDecQTYKTwCvnjrZzr0HjhPz0LWBYOhtPsK8RUSloESrh0cAR9hoSRNyoA-I802TEXy2p3-I5-Vm2CbjyQAvrCNeUVVVt1EPk303Eo_2KAra4UpIZNm5i2hJ8Xp
        VkToken token = App.getInstance().getToken();
        try {
            // TODO: Combine these 2 lines
            List<AudioModel> audioList = vkClient.getAudios(token);
            playlistView.setItems(FXCollections.observableArrayList(audioList));
            //        audioList.stream().map(a -> a.toString()).collect(Collectors.toList())));
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (AccessTokenFailedException e) {
            App.getInstance().logout();
        }

    }

    @FXML
    public void onPlayClick(ActionEvent actionEvent) throws JavaLayerException {
        if (audioPlayer.isPlaying()) {
            audioPlayer.pause();
            playButton.setText("Play");
        } else if (audioPlayer.isPaused()) {
            audioPlayer.play();
            playButton.setText("Pause");
        }
    }

    @FXML
    public void onPlaylistViewMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            AudioModel audio = playlistView.getSelectionModel().getSelectedItem();
            try {
                changeAudio(audio);
                System.out.println("Audio changed to: " + audio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setTimeLabelText(String currentTime, String totalTime) {
        String timeLabelText = String.format(TIME_PATTERN, currentTime, totalTime);
        timeLabel.setText(timeLabelText);
    }

    private void changeAudio(AudioModel audio) throws IOException, JavaLayerException {
        if (audioPlayer.isPlaying()) {
            audioPlayer.pause();
        }
        audioPlayer.changeAudio(audio);
        audioPlayer.getMeadiaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                if (!timeSlider.isValueChanging()) {
                    timeSlider.setValue(newValue.toSeconds());
                }
                setTimeLabelText(getTimeText((int) newValue.toSeconds()), getTimeText(audio.getDuration()));
            }
        });
        timeSlider.setMax(audio.getDuration());
        // Reset components
        titleLabel.setText(audio.toString());
        setTimeLabelText(getTimeText(0), getTimeText(audio.getDuration()));
        if (audioPlayer.isPlaying()) {
            playButton.setText("Play");
        } else if (audioPlayer.isPaused()) {
            playButton.setText("Pause");
        }
        if (playButton.isDisable()) {
            playButton.setDisable(false);
        }
    }

    private String getTimeText(int seconds) {
        return TImeUtils.getFormattedTime(seconds);
    }
}
