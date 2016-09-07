package com.dimkonko.vkplayer.task;

import com.dimkonko.jvkapi.model.VkToken;
import com.dimkonko.vkplayer.App;
import com.dimkonko.vkplayer.api.VkClient;
import com.dimkonko.vkplayer.exception.AccessTokenFailedException;
import com.dimkonko.vkplayer.exception.BadRequestException;
import com.dimkonko.vkplayer.model.json.AudioModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

public class LoadAudioListTask extends Task<ObservableList<AudioModel>> {
    @Override
    protected ObservableList<AudioModel> call() throws Exception {
        ObservableList<AudioModel> audios = null;
        VkToken token = App.getInstance().getToken();
        try {
            audios = FXCollections.observableArrayList(VkClient.getInstance().getAudios(token));
            //        audioList.stream().map(a -> a.toString()).collect(Collectors.toList())));
        } catch (BadRequestException e) {
            e.printStackTrace();
        } catch (AccessTokenFailedException e) {
            App.getInstance().logout();
        }
        return audios;
    }
}
