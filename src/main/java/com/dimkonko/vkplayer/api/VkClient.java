package com.dimkonko.vkplayer.api;

import com.dimkonko.jvkapi.api.VkAudioAPI;
import com.dimkonko.jvkapi.model.VkError;
import com.dimkonko.jvkapi.model.VkResponse;
import com.dimkonko.jvkapi.model.VkToken;
import com.dimkonko.vkplayer.exception.AccessTokenFailedException;
import com.dimkonko.vkplayer.exception.BadRequestException;
import com.dimkonko.vkplayer.model.json.AudioModel;
import com.dimkonko.vkplayer.service.JsonMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.util.List;

public class VkClient {

    private static final int OK_CODE = 200;
    private static final VkClient INSTANCE = new VkClient();

    private final VkAudioAPI api = new VkAudioAPI();

    public static VkClient getInstance() {
        return INSTANCE;
    }

    public List<AudioModel> getAudios(VkToken token) throws BadRequestException, AccessTokenFailedException {
        VkResponse response = api.getAudioList(token);
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
                    throw new BadRequestException();
            }
        }
    }
}
