package com.dimkonko.vkplayer.controller;

import com.dimkonko.vkplayer.model.json.AudioModel;
import com.dimkonko.vkplayer.player.AudioPlayer;
import com.dimkonko.vkplayer.service.TImeUtils;
import com.dimkonko.vkplayer.task.LoadAudioListTask;
import com.sun.istack.internal.NotNull;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for musicView.fxml
 */
public class MusicController implements Initializable {
    private static final String TIME_PATTERN = "%s/%s";

    @FXML private Label titleLabel;
    @FXML private Button prevButton;
    @FXML private Button playButton;
    @FXML private Button nextButton;
    @FXML public Slider timeSlider;
    @FXML private Label timeLabel;
    @FXML public ListView<AudioModel> playlistView;

    private final AudioPlayer audioPlayer = new AudioPlayer();

    private int audioInPlaylistIndex;
    private Control[] clickableControls;
    private boolean isAudioInitialized;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clickableControls = new Control[] {prevButton, playButton, nextButton, timeSlider, playlistView};
        resetControls();
        loadAudio();
    }

    /**
     * Resets controls to default state and values
     */
    private void resetControls() {
        playButton.setText("Play");
        toggleClickableControls(true);
        setTimeLabelText(TImeUtils.ZERO_TIME, TImeUtils.ZERO_TIME);
        audioInPlaylistIndex = -1;
    }

    /**
     * Enables or disables clickable controls by given param
     * @param isDisabled value to ael for all clickable elements
     */
    private void toggleClickableControls(boolean isDisabled) {
        for (Control control : clickableControls) {
            control.setDisable(isDisabled);
        }
    }

    private void loadAudio() {
        LoadAudioListTask loadAudioListTask = new LoadAudioListTask();
        loadAudioListTask.setOnSucceeded(event -> {
            playlistView.setItems(loadAudioListTask.getValue());
            playlistView.setDisable(false);
        });
        new Thread(loadAudioListTask).start();
    }


    @FXML
    public void onPrevClick() {
        int prevItem = audioInPlaylistIndex - 1;
        if (prevItem >= 0) {
            playlistView.getSelectionModel().select(prevItem);
            changeAudio(playlistView.getItems().get(prevItem));
            if (nextButton.isDisable()) {
                nextButton.setDisable(false);
            }
        } else {
            prevButton.setDisable(true);
        }
    }

    @FXML
    public void onNextClick() {
        int nextItem = audioInPlaylistIndex + 1;
        if (nextItem < playlistView.getItems().size()) {
            playlistView.getSelectionModel().select(nextItem);
            changeAudio(playlistView.getItems().get(nextItem));
            if (prevButton.isDisable()) {
                prevButton.setDisable(false);
            }
        } else {
            nextButton.setDisable(true);
        }
    }

    @FXML
    protected void onPlayClick() {
        if (audioPlayer.isPlaying()) {
            audioPlayer.pause();
            playButton.setText("Play");
        } else if (audioPlayer.isPaused()) {
            audioPlayer.play();
            playButton.setText("Pause");
        }
    }

    @FXML
    protected void onTimeSliderMouseReleased() {
        audioPlayer.seek(new Duration(timeSlider.getValue() * 1000));
    }

    @FXML
    protected void onPlaylistViewMouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            if (!isAudioInitialized) {
                isAudioInitialized = true;
                toggleClickableControls(false);
            }
            AudioModel audio = playlistView.getSelectionModel().getSelectedItem();
            // Check if audio selected, because list view can be empty
            if (audio != null) {
                changeAudio(audio);
                System.out.println("Audio changed to: " + audio);
            }
        }
    }

    /**
     * Checks prev. and next. button states.
     * Disable prev. button if current audio in playlist is a first or enable if not.
     * Disable next. button if current audio in playlist is a last or enable if not.
     */
    private void checkPrevNextButtonStates() {
        if (audioInPlaylistIndex == 0) {
            prevButton.setDisable(true);
        } else if (prevButton.isDisable()) {
            prevButton.setDisable(false);
        } else if (audioInPlaylistIndex == playlistView.getItems().size() - 1) {
            nextButton.setDisable(true);
        } else if (nextButton.isDisable()) {
            nextButton.setDisable(false);
        }
    }

    /**
     * Change audio in player with given audio
     * @param audio audio, which will be played
     */
    private void changeAudio(@NotNull AudioModel audio) {
        audioInPlaylistIndex = playlistView.getSelectionModel().getSelectedIndex();
        checkPrevNextButtonStates();
        // Change audio in the player
        audioPlayer.changeAudio(audio);
        audioPlayer.getMeadiaPlayer().currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (!timeSlider.isValueChanging() && !timeSlider.isPressed()) {
                timeSlider.setValue(newValue.toSeconds());
            }
            setTimeLabelText(TImeUtils.getFormattedTime((int) newValue.toSeconds()),
                    TImeUtils.getFormattedTime(audio.getDuration()));
        });
        // Update UI
        timeSlider.setMax(audio.getDuration());
        titleLabel.setText(audio.toString());
        setTimeLabelText(TImeUtils.ZERO_TIME, TImeUtils.getFormattedTime(audio.getDuration()));
        if (audioPlayer.isPlaying()) {
            playButton.setText("Play");
        } else if (audioPlayer.isPaused()) {
            playButton.setText("Pause");
        }
        if (playButton.isDisable()) {
            playButton.setDisable(false);
        }
        // Play new audio
        audioPlayer.play();
    }

    private void setTimeLabelText(String currentTime, String totalTime) {
        String timeLabelText = String.format(TIME_PATTERN, currentTime, totalTime);
        timeLabel.setText(timeLabelText);
    }
}
