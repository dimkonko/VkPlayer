package com.dimkonko.vkplayer;

import java.io.IOException;

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
        if (authService.isAuthorized()) {
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
// 887ee04e5edd8e303c619641e985fafef05d2fe4d696809a37fa263734e0853d1a26bf87b7bbe71a3ce6f
    public void login(VkUser vkUser) {
        authService.authorize(vkUser);
        changeScene(SceneType.MUSIC_VIEW);
    }

    public void logout() {
        changeScene(SceneType.LOGIN);
    }

    public VkUser getUser() {
        return authService.getUser();
    }

    public static App getInstance() {
        return instance;
    }
}
