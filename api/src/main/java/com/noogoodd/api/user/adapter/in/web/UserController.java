package com.noogoodd.api.user.adapter.in.web;

import com.noogoodd.api.user.adapter.in.model.LoginAuthenticationRequest;
import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.api.user.application.port.in.GetUserUseCase;
import com.noogoodd.api.user.application.port.in.SetUserUserCase;
import com.noogoodd.api.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/get")
    public UserDto getUser(HttpServletRequest request) throws Exception {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new Exception("Invalid token");
        }

        String jwt = authorizationHeader.substring(7);
        Long userIdFromToken = jwtUtil.extractUserId(jwt);

        return getUserUseCase.getUserById(userIdFromToken);
    }

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserDto userDto) {
        return setUserUseCase.registerUser(userDto);
    }

    @PostMapping("/update")
    public UserDto updateUser(HttpServletRequest request, @RequestBody UserDto userDto) throws Exception {
        final String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new Exception("Invalid token");
        }

        String jwt = authorizationHeader.substring(7);
        Long userIdFromToken = jwtUtil.extractUserId(jwt);

        return setUserUseCase.updateUser(userIdFromToken, userDto);
    }

    @PostMapping("/login")
    public String createAuthenticationToken(@RequestBody LoginAuthenticationRequest authenticationRequest) throws Exception {
        UserDto user;
        try {
            user = getUserUseCase.getUserByUsername(authenticationRequest.getUsername());
            if (user == null) {
                throw new BadCredentialsException("Invalid username");
            }
            if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                throw new BadCredentialsException("Invalid password");
            }
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password");
        } catch (Exception e) {
            throw new Exception("Exception occured during authentication");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        return jwtUtil.generateToken(user.getId(), userDetails);
    }
}
