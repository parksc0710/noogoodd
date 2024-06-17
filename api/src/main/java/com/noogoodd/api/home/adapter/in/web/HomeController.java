package com.noogoodd.api.home.adapter.in.web;

import com.noogoodd.api.home.application.port.in.GetHomeUseCase;
import com.noogoodd.api.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    private final JwtUtil jwtUtil;
    private final GetHomeUseCase getHomeUseCase;

    @PostMapping("/main")
    public ResponseEntity<?> getMain(HttpServletRequest request) {
        Long userIdFromToken = 0L;
        try {
            userIdFromToken = jwtUtil.checkJWTToken(request);
        } catch(Exception ignored) {
        }

        return ResponseEntity.ok(getHomeUseCase.geHomeMainData(userIdFromToken));
    }


}
