package com.noogoodd.api.user.application.port.in;


import com.noogoodd.api.user.domain.User;

public interface OAuth2UseCase {
    String getAccessToken(String code);
    User getUserInfo(String accessToken);
}
