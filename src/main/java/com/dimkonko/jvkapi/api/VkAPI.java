package com.dimkonko.jvkapi.api;

import com.dimkonko.jvkapi.etc.VkUrlBuilder;
import com.dimkonko.jvkapi.model.VkResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class VkAPI {
    private static final String API_URL = "https://api.vk.com/method/";

    /**
     * Build url by given params and send request by that url
     * @param method vk api method
     * @param arguments key value pairs, which will be in url as arguments
     * @return vk response
     */
    protected VkResponse call(String method, Map<String, String> arguments) {
        String url = VkUrlBuilder.create(API_URL.concat(method), arguments);
        return sendReq(url);
    }

    private VkResponse sendReq(String urlStr) {
        VkResponse response = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int respCode = con.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer strBuffer = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                strBuffer.append(inputLine);
            }
            in.close();

            response = new VkResponse(respCode, strBuffer.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
