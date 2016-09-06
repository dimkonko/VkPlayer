package net.dimkonko.vkmlj.etc;

import com.dimkonko.vkplayer.model.json.AudioModel;

public class AudioFactory {

    public static AudioModel createAudio(String json) {
        return (AudioModel) MapperUtils.parse(json, AudioModel.class);
    }
}
