package com.dimkonko.jvkapi.service;

import com.dimkonko.jvkapi.model.VkUser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

public class EncryptService {

    public static byte[] encode(VkUser user) {
        String data = user.getAccessToken() + ";" + user.getUserId();
        return Base64.getEncoder().encode(data.getBytes());
    }

    public static VkUser decode(Path path) throws IOException {
        byte[] fileBytes = Files.readAllBytes(path);
        String[] userData = new String(Base64.getDecoder().decode(fileBytes)).split(";");
        return VkUserFactory.createUser(userData[0], userData[1]);
    }
}
