package com.dimkonko.jvkapi.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class VkUrl {

    private URL url;
    private final Map<String, String> arguments = new HashMap<String, String>();

    public VkUrl(URL url) {
        this.url = url;
    }

    public VkUrl(String url) throws MalformedURLException {
        this(new URL(url));
    }

    public void process() {
        createArgumentsMap();
    }

    /**
     * Creates arguments map (e.g. everything after ?).
     * Example: ?user=John&id=12
     */
    private void createArgumentsMap() {
        String args = url.getRef();

        String[] pairs = args.split("&");
        for (String kwpair : pairs) {
            String[] kwargs = kwpair.split("=");
            arguments.put(kwargs[0],kwargs[1]);
        }
    }

    public Map<String, String> getArgs() {
        return arguments;
    }

    public String getRef() {
        return url.getRef();
    }

    public String getPath() {
        return url.getPath();
    }

    public URL getUrl() {
        return url;
    }
}
