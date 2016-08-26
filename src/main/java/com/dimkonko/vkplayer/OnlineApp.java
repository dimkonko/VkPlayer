package com.dimkonko.vkplayer;

import java.io.IOException;

import com.dimkonko.jvkapi.model.VkUser;
import com.dimkonko.vkplayer.scene.SceneType;

public interface OnlineApp {
    void changeScene(SceneType sceneType) throws IOException;
    void login(VkUser vkUser);
    void logout();
    VkUser getUser();
}
