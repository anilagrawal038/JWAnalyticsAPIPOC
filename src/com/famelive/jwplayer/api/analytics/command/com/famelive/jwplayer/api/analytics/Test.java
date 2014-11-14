package com.famelive.jwplayer.api.analytics.command.com.famelive.jwplayer.api.analytics;

import com.famelive.jwplayer.api.analytics.command.JWRequestCO;

import java.util.Map;

/**
 * Created by anil on 14/11/14.
 */
public class Test {
    public static void main(String[] args) {
        GenerateJWAPIRequest generateJWAPIRequest = new GenerateJWAPIRequest();
        JWRequestCO requestCO = generateJWAPIRequest.generateJWAPIRequestCO();
        Map<String, String> requestParamsMap = generateJWAPIRequest.fetchMapFromJWRequestCO(requestCO, true);
        Map<String, String> utf8EncodedRequestParams = generateJWAPIRequest.encodeToUTF8(requestParamsMap);
        Map<String, String> urlEncodedRequestParams = generateJWAPIRequest.encodeToURLFormat(utf8EncodedRequestParams);
        String requestParamsString = generateJWAPIRequest.generateRequestParametersString(urlEncodedRequestParams);
        System.out.println(requestParamsString);
    }
}
