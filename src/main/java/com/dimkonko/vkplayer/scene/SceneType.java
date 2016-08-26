package com.dimkonko.vkplayer.scene;

/**
 * Represents type of scene.
 */
public enum SceneType {
    LOGIN("loginView.fxml"),
    MUSIC_VIEW("musicView.fxml");

    private final String _fxmlFileName;

    SceneType(String fxmlFileName) {
        _fxmlFileName = fxmlFileName;
    }

    public String getFxmlFileName() {
        return _fxmlFileName;
    }
}
