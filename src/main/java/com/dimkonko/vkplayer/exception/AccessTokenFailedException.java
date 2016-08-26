package com.dimkonko.vkplayer.exception;

import com.dimkonko.jvkapi.model.VkError;

public class AccessTokenFailedException extends Exception {

    public AccessTokenFailedException(VkError error) {
        super(error.getMsg());
    }
}
