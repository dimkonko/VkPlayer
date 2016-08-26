package com.dimkonko.vkplayer.exception;

import com.dimkonko.jvkapi.model.VkError;

public class BadRequestException extends Exception {

    public BadRequestException() {}

    public BadRequestException(VkError error) {
        super(error.getMsg());
    }
}
