package com.dimkonko.vkplayer.service.auth;

import com.dimkonko.jvkapi.model.VkToken;
import com.dimkonko.jvkapi.service.VkTokenFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class EncryptService {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final String SEPARATOR = ",";

    public static byte[] encode(VkToken token) {
        StringBuilder builder = new StringBuilder();
        builder.append(token.getToken()).append(SEPARATOR)
                .append(token.getUserId()).append(SEPARATOR)
                .append(DATE_FORMAT.format(token.getExpireDate())).append(SEPARATOR);
        return Base64.getEncoder().encode(builder.toString().getBytes());
    }

    public static VkToken decode(Path path) throws IOException {
        String[] data = new String(Base64.getDecoder().decode(Files.readAllBytes(path))).split(SEPARATOR);

        Date expiresIn = null;
        try {
            expiresIn = DATE_FORMAT.parse(data[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return VkTokenFactory.createToken(data[0], data[1], expiresIn);
    }
}
