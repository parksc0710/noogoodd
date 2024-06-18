package com.noogoodd.api.user.adapter.in.web;

import com.noogoodd.api.user.adapter.in.model.LoginAuthenticationRequest;
import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.api.user.application.port.in.GetUserUseCase;
import com.noogoodd.api.user.application.port.in.SetUserUserCase;
import com.noogoodd.api.user.domain.User;
import com.noogoodd.api.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final GetUserUseCase getUserUseCase;
    private final SetUserUserCase setUserUseCase;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/get")
    public ResponseEntity<?> getUser(HttpServletRequest request) {
        Long userIdFromToken;
        try {
            userIdFromToken = jwtUtil.checkJWTToken(request);
        } catch(Exception e) {
            return new ResponseEntity<>("BAD_REQUEST", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(getUserUseCase.getUserById(userIdFromToken));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(setUserUseCase.registerUser(userDto));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody UserDto userDto) {
        Long userIdFromToken;
        try {
            userIdFromToken = jwtUtil.checkJWTToken(request);
        } catch(Exception e) {
            return new ResponseEntity<>("BAD_REQUEST", HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(setUserUseCase.updateUser(userIdFromToken, userDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> createAuthenticationToken(@RequestBody LoginAuthenticationRequest authenticationRequest) {
        UserDto user;
        String userEmail = authenticationRequest.getEmail();
        String userType = authenticationRequest.getType();
        try {
            user = getUserUseCase.getUserByUserEmail(userEmail, userType);
            if (user == null) {
                return new ResponseEntity<>("BAD_REQUEST", HttpStatus.BAD_REQUEST);
            }
            if(userType.equals("NORMAL") && !passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                return new ResponseEntity<>("BAD_REQUEST", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("BAD_REQUEST", HttpStatus.BAD_REQUEST);
        }

        final User userDomain = getUserUseCase.getUserServiceByUserEmail(userEmail, userType);

        return ResponseEntity.ok(jwtUtil.generateToken(user.getId(), userDomain));
    }
}
