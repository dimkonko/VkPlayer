package com.dimkonko.jvkapi.model;

import com.sun.istack.internal.NotNull;

@Deprecated
public class VkUser {

    private final String accessToken;
    private final String userId;

    public VkUser(@NotNull String accessToken, @NotNull String userId) {
        this.accessToken = accessToken;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
