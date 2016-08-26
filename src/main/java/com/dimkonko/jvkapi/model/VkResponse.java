package com.dimkonko.jvkapi.model;

public final class VkResponse {
    private final int statusCode;
    private final String body;

    public VkResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }
}
