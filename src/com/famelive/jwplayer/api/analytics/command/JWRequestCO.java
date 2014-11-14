package com.famelive.jwplayer.api.analytics.command;

/**
 * Created by anil on 14/11/14.
 */
public class JWRequestCO {
    private String api_key;
    private String api_timestamp;
    private String api_nonce;
    private String api_signature;
    private String api_format;

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_timestamp() {
        return api_timestamp;
    }

    public void setApi_timestamp(String api_timestamp) {
        this.api_timestamp = api_timestamp;
    }

    public String getApi_nonce() {
        return api_nonce;
    }

    public void setApi_nonce(String api_nonce) {
        this.api_nonce = api_nonce;
    }

    public String getApi_signature() {
        return api_signature;
    }

    public void setApi_signature(String api_signature) {
        this.api_signature = api_signature;
    }

    public String getApi_format() {
        return api_format;
    }

    public void setApi_format(String api_format) {
        this.api_format = api_format;
    }
}
