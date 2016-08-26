package com.dimkonko.vkplayer.api;

import com.dimkonko.jvkapi.api.VkAudioAPI;
import com.dimkonko.jvkapi.api.VkAuthApi;
import com.dimkonko.jvkapi.model.VkError;
import com.dimkonko.jvkapi.model.VkResponse;
import com.dimkonko.jvkapi.model.VkUser;
import com.dimkonko.vkplayer.exception.AccessTokenFailedException;
import com.dimkonko.vkplayer.exception.BadRequestException;
import com.dimkonko.vkplayer.json.model.AudioModel;
import com.dimkonko.vkplayer.service.JsonMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

public class VkClient {

    private static final int OK_CODE = 200;
    private static final VkClient INSTANCE = new VkClient();

    private final VkAudioAPI api = new VkAudioAPI();
    private final VkAuthApi authApi = new VkAuthApi();

    public static VkClient getInstance() {
        return INSTANCE;
    }

    public boolean isAutenticated(VkUser user) throws BadRequestException {
        boolean isAutenticated = false;
        VkResponse response = authApi.isAutenticated(user);
        validateResponseCode(response);

        JsonNode jsonResponse = JsonMapper.parseObject(response.getBody());
        try {
            validateResponseJson(jsonResponse);
            isAutenticated = true;
        } catch (AccessTokenFailedException e) {
            isAutenticated = false;
        }

        return isAutenticated;
    }

    public List<AudioModel> getAudios(VkUser user) throws BadRequestException, AccessTokenFailedException {
        VkResponse response = api.getAudioList(user);
        validateResponseCode(response);

        JsonNode jsonResponse = JsonMapper.parseObject(response.getBody());
        validateResponseJson(jsonResponse);

        JsonNode audioListJson = jsonResponse.get("response");
        return JsonMapper.parseAudioList((ArrayNode) audioListJson);
    }

    private void validateResponseCode(VkResponse response) throws BadRequestException {
        if (response.getStatusCode() != OK_CODE) {
            throw new BadRequestException();
        }
    }

    private void validateResponseJson(JsonNode json) throws BadRequestException, AccessTokenFailedException {
        if (json.has("error")) {
            VkError error = JsonMapper.parseError(json.get("error"));

            switch (error.getCode()) {
                case 5:
                    throw new AccessTokenFailedException(error);
                default:
                    new BadRequestException();
            }
        }
    }
}
