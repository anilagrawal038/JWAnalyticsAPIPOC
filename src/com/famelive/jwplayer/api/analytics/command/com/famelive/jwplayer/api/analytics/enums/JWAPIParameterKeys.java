package com.famelive.jwplayer.api.analytics.command.com.famelive.jwplayer.api.analytics.enums;

/**
 * Created by anil on 14/11/14.
 */
public enum JWAPIParameterKeys {

    API_KEY("api_key"),
    API_TIMESTAMP("api_timestamp"),
    API_NONCE("api_nonce"),
    API_SIGNATURE("api_signature"),
    API_FORMAT("api_format");

    private String key;

    JWAPIParameterKeys(String key) {
        this.setKey(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
