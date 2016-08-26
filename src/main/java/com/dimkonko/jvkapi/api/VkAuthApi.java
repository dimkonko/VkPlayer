package com.dimkonko.jvkapi.api;

import com.dimkonko.jvkapi.etc.VkFields;
import com.dimkonko.jvkapi.model.VkResponse;
import com.dimkonko.jvkapi.model.VkUser;

import java.util.HashMap;
import java.util.Map;

public class VkAuthApi extends VkAPI {

    /**
     * Checks if user token not expired
     * @param user
     * @return
     */
    public VkResponse isAutenticated(VkUser user) {
        Map<String, String> args = new HashMap<>();
        args.put(VkFields.ACCESS_TOKEN, user.getAccessToken());

        return this.call("secure", args);
    }
}
