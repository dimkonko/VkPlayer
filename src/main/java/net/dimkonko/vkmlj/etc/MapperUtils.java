package net.dimkonko.vkmlj.etc;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class MapperUtils {

    private static final ObjectMapper MAPPER = createMapper();

    public static Object parse(String json, Class type) {
        Object o = null;
        try {
            o = MAPPER.readValue(json, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static Object readFromFile(File file, Class type) {
        Object o = null;
        try {
            o = MAPPER.readValue(file, type);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return o;
    }

    public static void saveFile(File file, Object o) {
        try {
            MAPPER.writeValue(file, o);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static ObjectMapper createMapper() {
        return new ObjectMapper();
    }
}
