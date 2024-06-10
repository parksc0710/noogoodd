package com.noogoodd.api.user.adapter.out.oauth2;

import com.noogoodd.api.user.application.port.in.OAuth2UseCase;
import com.noogoodd.api.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GoogleOAuth2Adapter implements OAuth2UseCase {

    @Override
    public String getAccessToken(String code) {
        // Call Google OAuth2 API to get access token
        return "google-access-token";
    }

    @Override
    public User getUserInfo(String accessToken) {
        // Call Google API to get user info
        return new User();
    }
}
