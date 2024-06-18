package com.noogoodd.api.user.adapter.in.web;

import com.noogoodd.api.user.application.dto.SNSLoginRequest;
import com.noogoodd.api.user.application.dto.SNSLoginResponse;
import com.noogoodd.api.user.application.service.OAuth2UserService;
import com.noogoodd.api.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/sns-login")
@RequiredArgsConstructor
public class SNSLoginController {

    private final OAuth2UserService oAuth2UserService;

    @PostMapping("/{provider}")
    public SNSLoginResponse login(@PathVariable String provider, @RequestBody SNSLoginRequest request) {
        User user = oAuth2UserService.login(provider, request.getToken());
        return new SNSLoginResponse(user.getUuid(), user.getEmail(), user.getSign_type());
    }

}
