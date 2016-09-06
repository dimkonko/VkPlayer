package com.dimkonko.vkplayer;

import java.io.IOException;

import com.dimkonko.jvkapi.model.VkToken;
import com.dimkonko.vkplayer.service.auth.AuthService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.dimkonko.jvkapi.model.VkUser;
import com.dimkonko.vkplayer.scene.SceneType;

public class App extends Application {

    public static final String APP_ID = "4585679";

    private final AuthService authService;
    private static App instance;

    private Stage _stage;

    public App() {
        instance = this;
        authService = new AuthService();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        _stage = primaryStage;

        authService.loadToken();
        if (authService.isAuthorized() && !authService.sessionExpired()) {
            changeScene(SceneType.MUSIC_VIEW);
        } else {
            changeScene(SceneType.LOGIN);
        }
        primaryStage.setTitle("Vk Player");
        primaryStage.show();
    }

    private void changeScene(SceneType sceneType) {
        Parent view = null;
        try {
            view = FXMLLoader.load(getClass().getClassLoader().getResource(sceneType.getFxmlFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        _stage.setScene(new Scene(view));
        _stage.sizeToScene();
        _stage.show();
    }

    public void login(VkToken token) {
        authService.authorize(token);
        changeScene(SceneType.MUSIC_VIEW);
    }

    public void logout() {
        changeScene(SceneType.LOGIN);
    }

    public VkToken getToken() {
        return authService.getToken();
    }

    public static App getInstance() {
        return instance;
    }
}
