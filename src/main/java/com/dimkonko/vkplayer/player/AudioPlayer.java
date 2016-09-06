package com.dimkonko.vkplayer.player;

import com.dimkonko.vkplayer.model.json.AudioModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javazoom.jl.decoder.JavaLayerException;

public class AudioPlayer {

    private MediaPlayer player;
    private int currentTime = 0;

    public AudioPlayer() {
        currentTime = 0;
    }

    public boolean isPaused() {
        return player != null && player.getStatus() != MediaPlayer.Status.PLAYING;
    }

    public boolean isPlaying() {
        return player != null && player.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void changeAudio(AudioModel audio) {
        player = new MediaPlayer(new Media(audio.getUrl()));
        player.setAutoPlay(true);
    }

    public void play() throws JavaLayerException {
        player.play();
    }

    public void pause() {
        player.pause();
    }

    public MediaPlayer getMeadiaPlayer() {
        return player;
    }

    public void seek(Duration duration) {
        player.seek(duration);
    }
}
