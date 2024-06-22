package com.noogoodd.api.user.adapter.out.oauth2;

import com.noogoodd.api.user.application.port.in.OAuth2UseCase;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleOAuth2Adapter implements OAuth2UseCase {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInfoUri;

    @Override
    public String getAccessToken(String code) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(tokenUri);

        List<NameValuePair> urlParameters = new ArrayList<>();
        urlParameters.add(new BasicNameValuePair("code", code));
        urlParameters.add(new BasicNameValuePair("client_id", clientId));
        urlParameters.add(new BasicNameValuePair("client_secret", clientSecret));
        urlParameters.add(new BasicNameValuePair("redirect_uri", redirectUri));
        urlParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));

        post.setEntity(new UrlEncodedFormEntity(urlParameters));
        post.setHeader("Content-Type", "application/x-www-form-urlencoded");

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(post)) {

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                // 오류 응답을 로그로 출력하여 문제를 디버깅합니다.
                String errorResponse = EntityUtils.toString(response.getEntity());
                System.err.println("Error response: " + errorResponse);
                throw new RuntimeException("Failed to get access token, HTTP error code: " + statusCode);
            }

            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(responseString);
            return json.getString("access_token");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUserInfo(String accessToken) {
        String oauthUserEamil;

        HttpGet get = new HttpGet(userInfoUri);
        get.setHeader("Authorization", "Bearer " + accessToken);

        try (CloseableHttpClient client = HttpClients.createDefault();
             CloseableHttpResponse response = client.execute(get)) {
            String responseString = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(responseString);

            oauthUserEamil = json.getString("email");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return oauthUserEamil;
    }
}
