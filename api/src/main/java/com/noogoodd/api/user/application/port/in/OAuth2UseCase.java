package com.noogoodd.api.user.application.port.in;


import com.noogoodd.api.user.domain.User;

import java.io.UnsupportedEncodingException;

public interface OAuth2UseCase {
    String getAccessToken(String code) throws UnsupportedEncodingException;
    String getUserInfo(String accessToken);
}
