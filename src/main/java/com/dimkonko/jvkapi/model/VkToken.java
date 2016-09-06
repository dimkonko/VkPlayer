package com.dimkonko.jvkapi.model;

import java.util.Date;

public class VkToken {
    private final String token;
    private final String userId;
    private final Date expireDate;

    public VkToken(String token, String userId, Date expireDate) {
        this.token = token;
        this.userId = userId;
        this.expireDate = expireDate;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public boolean hasExpired() {
        return new Date().after(expireDate);
    }
}
