package com.dimkonko.jvkapi.service;

import com.dimkonko.jvkapi.etc.VkConstants;
import com.dimkonko.jvkapi.model.VkToken;

import java.util.Date;
import java.util.Map;

public class VkTokenFactory {

    public static VkToken createToken(Map<String, String> args) {
        // Convert expiresIn seconds to milliseconds like: seconds * 1000
        // 1000 - numbers of milliseconds in one second
        Date expiresIn = new Date(System.currentTimeMillis() +
                Integer.parseInt(args.get(VkConstants.EXPIRES_IN))* 1000);
        return createToken(args.get(VkConstants.ACCESS_TOKEN), args.get(VkConstants.USER_ID), expiresIn);
    }

    public static VkToken createToken(String token, String userId, Date expiresIn) {
        return new VkToken(token, userId, expiresIn);
    }
}
