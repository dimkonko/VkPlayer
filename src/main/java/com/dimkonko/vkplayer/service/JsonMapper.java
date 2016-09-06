package com.dimkonko.vkplayer.service;

import com.dimkonko.jvkapi.model.VkError;
import com.dimkonko.vkplayer.model.json.AudioModel;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonMapper {

    private static final ObjectMapper MAPPER = createMapper();

    private static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper;
    }

    public static JsonNode parseObject(String json) {
        JsonNode node = null;
        try {
            node = MAPPER.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }

    public static List<AudioModel> parseAudioList(ArrayNode jsonAudioList) {
        List<AudioModel> audioModels = new ArrayList<>();
        jsonAudioList.remove(0); // this is a total audio object count
        for (JsonNode jsonAudio : jsonAudioList) {
//            try {
                audioModels.add(parseAudioModel(jsonAudio));
//            } catch (NullPointerException e) {
//                System.err.println("can't parse: " + jsonAudio.toString());
//                e.printStackTrace();
//            }
        }
        return audioModels;
    }

    public static AudioModel parseAudioModel(JsonNode jsonAudio) {
        return new AudioModel(jsonAudio.get("aid").asText(),
                jsonAudio.get("artist").asText(),
                jsonAudio.get("title").asText(),
                jsonAudio.get("url").asText(),
                jsonAudio.get("duration").asInt()
                // There  are some track, which doesn't have lyrics or genre fields
//                jsonAudio.get("lyrics_id").asText(),
//                jsonAudio.get("genre").asInt()
                );
    }

    public static VkError parseError(JsonNode jsonError) {
        return new VkError(jsonError.get("error_code").asInt(),
                jsonError.get("error_msg").asText());
    }
}
