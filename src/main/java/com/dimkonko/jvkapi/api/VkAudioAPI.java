package com.dimkonko.jvkapi.api;

import com.dimkonko.jvkapi.model.VkResponse;
import com.dimkonko.jvkapi.model.VkUser;
import com.dimkonko.jvkapi.etc.VkFields;

import java.util.HashMap;
import java.util.Map;

public class VkAudioAPI extends VkAPI {

    private static final String AUDIO_GET = "audio.get";

    public VkResponse getAudioList(VkUser user) {
        Map<String, String> args = new HashMap<>();
        args.put(VkFields.OWNER_ID, user.getUserId());
        args.put(VkFields.ACCESS_TOKEN, user.getAccessToken());

        return this.call(AUDIO_GET, args);
    }
}
