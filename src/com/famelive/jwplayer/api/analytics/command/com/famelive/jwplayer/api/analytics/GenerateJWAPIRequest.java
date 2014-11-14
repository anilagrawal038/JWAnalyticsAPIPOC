package com.famelive.jwplayer.api.analytics.command.com.famelive.jwplayer.api.analytics;

import com.famelive.jwplayer.api.analytics.command.JWRequestCO;
import com.famelive.jwplayer.api.analytics.command.com.famelive.jwplayer.api.analytics.constants.JWAPIConstants;
import com.famelive.jwplayer.api.analytics.command.com.famelive.jwplayer.api.analytics.enums.JWAPIParameterKeys;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by anil on 14/11/14.
 */

//Reference URL: http://apidocs.jwplayer.com/authentication.html
//Reference URL: http://support.jwplayer.com/customer/portal/articles/1489902-jw-platform-api-reference

public class GenerateJWAPIRequest {
    JWRequestCO generateJWAPIRequestCO() {
        JWRequestCO jwRequestCO = new JWRequestCO();
        jwRequestCO.setApi_key(JWAPIConstants.API_KEY);
        jwRequestCO.setApi_format(JWAPIConstants.API_FORMAT);
        jwRequestCO.setApi_timestamp(new Date().getTime() / 1000 + "");
        //Note: We need Unix time stamp, that is in seconds, whereas Date().getTime() returns milliseconds since Jan 1, 1970

        jwRequestCO.setApi_nonce(getJWAPINonce(JWAPIConstants.API_NONCE_LENGTH));
        jwRequestCO.setApi_signature(generateJWAPISignature(jwRequestCO));
        return jwRequestCO;
    }

    String getJWAPINonce(int nonceLength) {
        String nonce = "";
        int nonceLengthMultiplier = 1;
        for (int multiplier = 0; multiplier < nonceLength; multiplier++) {
            nonceLengthMultiplier *= 10;
        }
        int randomNo = (int) (Math.random() * nonceLengthMultiplier);
        nonce = randomNo + "";
        return nonce;
    }

    String generateJWAPISignature(JWRequestCO requestCO) {
        String signature = "";
        Map<String, String> requestParamsMap = fetchMapFromJWRequestCO(requestCO, false);
        Map<String, String> utf8EncodedRequestParams = encodeToUTF8(requestParamsMap);
        Map<String, String> urlEncodedRequestParams = encodeToURLFormat(utf8EncodedRequestParams);
        TreeMap<String, String> sortedRequestParams = sortRequestParameters(urlEncodedRequestParams);
        String requestParamsString = generateRequestParametersString(sortedRequestParams);
        String secretAppendedRequestParamsString = appendJWAPISecretToRequestParametersString(requestParamsString);
        signature = generateSHA1Digest(secretAppendedRequestParamsString);
        return signature;
    }

    Map<String, String> fetchMapFromJWRequestCO(JWRequestCO requestCO, boolean isIncludeAPISignature) {
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put(JWAPIParameterKeys.API_KEY.getKey(), requestCO.getApi_key());
        parameters.put(JWAPIParameterKeys.API_FORMAT.getKey(), requestCO.getApi_format());
        parameters.put(JWAPIParameterKeys.API_NONCE.getKey(), requestCO.getApi_nonce());
        parameters.put(JWAPIParameterKeys.API_TIMESTAMP.getKey(), requestCO.getApi_timestamp());
        if (isIncludeAPISignature) {
            parameters.put(JWAPIParameterKeys.API_SIGNATURE.getKey(), requestCO.getApi_signature());
        }
        return parameters;
    }

    Map<String, String> encodeToUTF8(Map<String, String> requestParams) {
        Map<String, String> parameters = new HashMap<String, String>();
        for (String key : requestParams.keySet()) {
            //parameters.put(StandardCharsets.UTF_8.decode(Charset.forName("UTF-8").encode(key)).toString(), StandardCharsets.UTF_8.decode(Charset.forName("UTF-8").encode(requestParams.get(key))).toString());
            parameters.put(StringUtils.newStringUtf8(key.getBytes(Charset.forName("UTF-8"))), StringUtils.newStringUtf8(requestParams.get(key).getBytes(Charset.forName("UTF-8"))));

        }
        return parameters;
    }

    Map<String, String> encodeToURLFormat(Map<String, String> requestParams) {
        Map<String, String> encodedParameters = new HashMap<String, String>();
        try {
            for (String key : requestParams.keySet()) {
                //encodedParameters.put(URLEncoder.encode(key, "UTF-8"), URLEncoder.encode(requestParams.get(key), "UTF-8"));
                URLCodec urlCodec = new URLCodec("UTF-8");
                encodedParameters.put(urlCodec.encode(key), urlCodec.encode(requestParams.get(key)));
            }
        }/* catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } */ catch (EncoderException e) {
            e.printStackTrace();
        }
        return encodedParameters;
    }

    TreeMap<String, String> sortRequestParameters(Map<String, String> requestParams) {
        //TreeMap contains sorted data on the basis of keys
        TreeMap<String, String> parameters = new TreeMap<String, String>(
                new Comparator<String>() {

                    @Override
                    public int compare(String o1, String o2) {
                        //For descending order
                        //return o2.compareTo(o1);

                        //For Ascending order
                        return o1.compareTo(o2);
                    }

                });
        //Note: Comparator is optional, by default ascending order will be used

        parameters.putAll(requestParams);
        return parameters;
    }

    String generateRequestParametersString(Map<String, String> requestParameters) {
        String parameters = "";
        for (String key : requestParameters.keySet()) {
            if (parameters.length() > 1) {
                parameters += "&";
            }
            parameters += key + "=" + requestParameters.get(key);
        }
        return parameters;
    }

    String appendJWAPISecretToRequestParametersString(String requestParametersString) {
        String resultant = requestParametersString + JWAPIConstants.API_SECRET_CODE;
        return resultant;
    }

    String generateSHA1Digest(String data) {

        String digest = DigestUtils.sha1Hex(data);
        /*try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.reset();
            messageDigest.update(data.getBytes("UTF-8"));
            digest = byteToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        return digest;
    }

    private static String byteToHex(final byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte byt : bytes) {
            formatter.format("%02x", byt);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }


}
