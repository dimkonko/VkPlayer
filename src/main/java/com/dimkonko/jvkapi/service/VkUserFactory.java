package com.dimkonko.jvkapi.service;

import com.dimkonko.jvkapi.model.VkUser;
import com.dimkonko.jvkapi.etc.VkConstants;

import java.util.Map;

public class VkUserFactory {

    public static VkUser createUser(Map<String, String> args) {
        return createUser(args.get(VkConstants.ACCESS_TOKEN), args.get(VkConstants.USER_ID));
    }

    public static VkUser createUser(String token, String userId) {
        return new VkUser(token, userId);
    }
}
