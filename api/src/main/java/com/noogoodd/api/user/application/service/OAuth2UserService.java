package com.noogoodd.api.user.application.service;

import com.noogoodd.api.user.application.port.in.GetUserUseCase;
import com.noogoodd.api.user.application.port.in.OAuth2UseCase;
import com.noogoodd.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class OAuth2UserService {

    private final OAuth2UseCase googleOAuth2Adapter;
    private final OAuth2UseCase kakaoOAuth2Adapter;
    private final OAuth2UseCase naverOAuth2Adapter;

    private final GetUserUseCase getUserUseCase;

    public Pair<String, User> login(String provider, String code) throws UnsupportedEncodingException {
        OAuth2UseCase oAuth2UseCase = switch (provider.toLowerCase()) {
            case "google" -> googleOAuth2Adapter;
            case "kakao" -> kakaoOAuth2Adapter;
            case "naver" -> naverOAuth2Adapter;
            default -> throw new IllegalArgumentException("Unknown provider: " + provider);
        };

        String accessToken = oAuth2UseCase.getAccessToken(code);
        String userEmail = oAuth2UseCase.getUserInfo(accessToken);
        User oAuthUser = null;
        try {
            oAuthUser = getUserUseCase.getUserServiceByUserEmail(userEmail, provider.toUpperCase());
        } catch (Exception ignored) {
        }
        return Pair.of(userEmail, oAuthUser);
    }

}
