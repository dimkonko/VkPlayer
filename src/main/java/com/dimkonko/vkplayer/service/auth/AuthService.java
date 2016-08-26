package com.dimkonko.vkplayer.service.auth;

import com.dimkonko.jvkapi.model.VkUser;
import com.dimkonko.vkplayer.api.VkClient;
import com.dimkonko.vkplayer.exception.BadRequestException;

public class AuthService {
    private final TokenLoader tokenLoader = new TokenLoader("conf/.token");

    private VkUser vkUser;

    public void loadToken() {
        vkUser = tokenLoader.loadToken();
    }

    public void authorize(VkUser user) {
        this.vkUser = user;
        tokenLoader.saveToken(user);
    }

    public VkUser getUser() {
        return vkUser;
    }

    public boolean isAuthorized() {
        return vkUser != null;
    }
/*
    public boolean isAuthorized() {
        boolean isAuthorized = false;
        try {
            isAuthorized = vkUser != null && VkClient.getInstance().isAutenticated(vkUser);
        } catch (BadRequestException e) {
            isAuthorized = false;
        }
        return isAuthorized;
    }*/
}
