package com.noogoodd.api.user.application.service;

import com.noogoodd.api.user.application.port.in.OAuth2UseCase;
import com.noogoodd.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final OAuth2UseCase googleOAuth2Adapter;
    private final OAuth2UseCase kakaoOAuth2Adapter;
    private final OAuth2UseCase naverOAuth2Adapter;

    public User login(String provider, String code) {
        OAuth2UseCase oAuth2UseCase = switch (provider.toLowerCase()) {
            case "google" -> googleOAuth2Adapter;
            case "kakao" -> kakaoOAuth2Adapter;
            case "naver" -> naverOAuth2Adapter;
            default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };

        String accessToken = oAuth2UseCase.getAccessToken(code);
        return oAuth2UseCase.getUserInfo(accessToken);
    }

}
