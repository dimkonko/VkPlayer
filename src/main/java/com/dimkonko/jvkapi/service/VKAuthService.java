package com.dimkonko.jvkapi.service;

import com.dimkonko.jvkapi.etc.VkConstants;
import com.dimkonko.jvkapi.model.VkUrl;
import com.sun.istack.internal.NotNull;

/**
 * Class to create OAUTH2 link
 */
public class VKAuthService {
    private static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    // TODO: create interface to init these values
    private static final String OAUTH_URL_PATTERN = "https://oauth.vk.com/authorize?" +
            "client_id=%1$s&" +
            "scope=audio&" +
            "redirect_uri=" + REDIRECT_URL + "&" +
            "display=popup&" +
            "v=5.30&" +
            "response_type=token";

    private final String _oauthUrl;

    public VKAuthService(@NotNull String appId) {
        _oauthUrl = String.format(OAUTH_URL_PATTERN, appId);
    }

    public String getAuthUrl() {
        return _oauthUrl;
    }

    public boolean wasAuthenticated(VkUrl url) {
        return url.getPath().equals(VkConstants.BLANK_HTML_PATH) && url.getRef() != null;
    }
}
