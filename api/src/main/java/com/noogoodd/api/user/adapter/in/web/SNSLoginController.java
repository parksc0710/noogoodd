package com.noogoodd.api.user.adapter.in.web;

import com.noogoodd.api.user.application.service.OAuth2UserService;
import com.noogoodd.api.user.domain.User;
import com.noogoodd.api.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sns-login")
@RequiredArgsConstructor
public class SNSLoginController {

    private final OAuth2UserService oAuth2UserService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{provider}")
    public ResponseEntity<String> login(@PathVariable String provider, @RequestBody String code) throws Exception {
        Pair<String, User> oAthUserPair = oAuth2UserService.login(provider, code);

        if(oAthUserPair.getRight() != null && oAthUserPair.getRight().getId() > 0) {
            return ResponseEntity.ok(jwtUtil.generateToken(oAthUserPair.getRight().getId(), oAthUserPair.getRight()));
        } else {
            return ResponseEntity.ok(oAthUserPair.getLeft());
        }
    }
}
