package com.dimkonko.vkplayer.service.auth;

import com.dimkonko.jvkapi.model.VkToken;

public class AuthService {
    private final TokenLoader tokenLoader = new TokenLoader("conf/.token");

    private VkToken vkToken;

    public void loadToken() {
        vkToken = tokenLoader.loadToken();
    }

    public void authorize(VkToken token) {
        this.vkToken = token;
        tokenLoader.saveToken(vkToken);
    }

    public boolean isAuthorized() {
        return vkToken != null;
    }

    public boolean sessionExpired() {
        return vkToken.hasExpired();
    }

    public VkToken getToken() {
        return vkToken;
    }
/*
    public boolean isAuthorized() {
        boolean isAuthorized = false;
        try {
            isAuthorized = vkToken != null && VkClient.getInstance().isAutenticated(vkToken);
        } catch (BadRequestException e) {
            isAuthorized = false;
        }
        return isAuthorized;
    }*/
}
