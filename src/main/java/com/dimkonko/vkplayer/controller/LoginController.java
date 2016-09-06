package com.dimkonko.vkplayer.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import com.dimkonko.vkplayer.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import com.dimkonko.jvkapi.model.VkUrl;
import com.dimkonko.jvkapi.service.VkTokenFactory;
import com.dimkonko.jvkapi.service.VKAuthService;

public class LoginController implements Initializable {

    @FXML
    private WebView webView;

    private final VKAuthService vkAuth = new VKAuthService(App.APP_ID);

    private WebEngine webEngine;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load(vkAuth.getAuthUrl());

        webEngine.getLoadWorker().stateProperty().addListener(new WebStatusListener());
    }

    private void processUrl(String urlStr) {
        try {
            VkUrl vkUrl = new VkUrl(webEngine.getLocation());
            if (vkAuth.wasAuthenticated(vkUrl)) {
                vkUrl.process();
                App.getInstance().login(VkTokenFactory.createToken(vkUrl.getArgs()));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    class WebStatusListener implements ChangeListener<Worker.State> {

        public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState,
                Worker.State newState) {
//            System.out.println("Loading " + observableValue.getValue());
            if (newState == Worker.State.SUCCEEDED) {
                processUrl(webEngine.getLocation());
            }
        }
    }
}
