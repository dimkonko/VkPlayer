package com.dimkonko.vkplayer.player;

public enum PlayerState {
    PLAY("Playing"),
    PAUSE("Paused");

    private final String labelText;

    PlayerState(String labelText) {
        this.labelText = labelText;
    }

    public String getLabelText() {
        return labelText;
    }
}
