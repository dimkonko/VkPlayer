package com.dimkonko.vkplayer.service.auth;

import com.dimkonko.jvkapi.model.VkToken;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TokenLoader {

    private final File tokenFile;

    public TokenLoader(String tokenPath) {
        this.tokenFile =  new File(tokenPath);
    }

    public void saveToken(VkToken token) {
        if (!tokenFile.exists()) {
            createTokenFile();
        }

        try {
            Files.write(Paths.get(tokenFile.getPath()), EncryptService.encode(token));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createTokenFile() {
        try {
            tokenFile.getParentFile().mkdirs();
            tokenFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VkToken loadToken() {
        VkToken token = null;
        if (tokenFile.exists()) {
            try {
                token = EncryptService.decode(Paths.get(tokenFile.getPath()));
                System.out.println("Token loaded");
                System.out.println("Token expire date: " + token.getExpireDate());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return token;
    }
}
