package com.dimkonko.jvkapi.etc;

import java.util.Iterator;
import java.util.Map;

public class VkUrlBuilder {
    private static final String AND_CHAR = "&";
    private static final String EQ_CHAR = "=";

    /**
     * Creates ur; from given arguments
     * @param head url head (i.e. http://api.vk.com)
     * @param arguments key value pairs, which goes after ? (i.e. http://...?user_id=123&count=4)
     * @return full url by given params
     */
    public static String create(String head, Map<String, String> arguments) {
        StringBuilder stringBuilder = new StringBuilder(head.concat("?"));

        Iterator<String> keyIter = arguments.keySet().iterator();
        while (keyIter.hasNext()) {
            String key = keyIter.next();
            String value = arguments.get(key);

            stringBuilder.append(key).append(EQ_CHAR).append(value);

            if (keyIter.hasNext()) {
                stringBuilder.append(AND_CHAR);
            }
        }
//        for (Map.Entry<String, Object> entry : arguments.entrySet()) {
//            stringBuilder.append(entry.getKey()).append(EQ_CHAR).append(entry.getValue());
//        }
        return stringBuilder.toString();
    }
}
