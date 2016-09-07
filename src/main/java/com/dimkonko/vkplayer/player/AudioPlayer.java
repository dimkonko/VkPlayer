package com.dimkonko.vkplayer.player;

import com.dimkonko.vkplayer.model.json.AudioModel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioPlayer {

    private MediaPlayer player;

    public boolean isPaused() {
        return player != null && player.getStatus() != MediaPlayer.Status.PLAYING;
    }

    public boolean isPlaying() {
        return player != null && player.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public void changeAudio(AudioModel audio) {
        if (player != null) {
            player.dispose();
        }
        player = new MediaPlayer(new Media(audio.getUrl()));
        player.setAutoPlay(false);
    }

    public void play() {
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
