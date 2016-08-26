package com.dimkonko.vkplayer.service.auth;

import com.dimkonko.jvkapi.model.VkUser;
import com.dimkonko.jvkapi.service.EncryptService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TokenLoader {

    private final File tokenFile;

    public TokenLoader(String tokenPath) {
        this.tokenFile =  new File(tokenPath);
    }

    private void createTokenFile() {
        try {
            tokenFile.getParentFile().mkdirs();
            tokenFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToken(VkUser user) {
        if (!tokenFile.exists()) {
            createTokenFile();
        }

        try {
            Files.write(Paths.get(tokenFile.getPath()), EncryptService.encode(user));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public VkUser loadToken() {
        VkUser user = null;
        if (tokenFile.exists()) {
            try {
                user = EncryptService.decode(Paths.get(tokenFile.getPath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
