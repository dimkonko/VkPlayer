package com.dimkonko.jvkapi.model;

public class VkError {
    private final int code;
    private final String msg;

    public VkError(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
