package net.dimkonko.vkmlj.controllers;

import com.dimkonko.jvkapi.model.VkUser;
import com.dimkonko.vkplayer.App;
import com.dimkonko.vkplayer.api.VkClient;
import com.dimkonko.vkplayer.exception.AccessTokenFailedException;
import com.dimkonko.vkplayer.exception.BadRequestException;
import com.dimkonko.vkplayer.json.model.AudioModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MusicController implements Initializable {

    @FXML
    private ListView<String> musicView;

    private final VkClient vkClient = VkClient.getInstance();
//    private final AudioLoader audioLoader = new AudioLoader("Downloads/");

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadAudio();
//        musicView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void loadAudio() {
        VkUser user = App.getInstance().getUser();
        try {
            List<AudioModel> audioList = vkClient.getAudios(user);
            List<String> audioStrings = audioList.stream().map(a -> a.toString()).collect(Collectors.toList());
            musicView.setItems(FXCollections.observableArrayList(audioStrings));
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (AccessTokenFailedException e) {
            App.getInstance().logout();
        }

    }
/*
    @Override
    public void setApp(OnlineApp app) {
        this.app = app;
    }

    @Override
    public void beforeChange() {
        VkUser user = app.getUser();
        String jsonResponse = api.getAudioList(user.getUserId(), user.getAccessToken());
        List<String> audioList = data.getAudio(jsonResponse);
        musicView.setItems(FXCollections.observableArrayList(audioList));

    }
*/
    @FXML
    protected void onDownloadSelected(ActionEvent event) {
//        System.out.println("Download all");
//        List<String> selectedAudios = musicView.getSelectionModel().getSelectedItems();
//        List<AudioModel> audioList = data.getAudioModels(selectedAudios);
//        for (AudioModel model : audioList) {
//            audioLoader.loadAudio(model);
//        }
    }
}
