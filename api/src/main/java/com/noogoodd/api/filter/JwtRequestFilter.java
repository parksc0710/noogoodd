package com.noogoodd.api.filter;

import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.api.user.application.service.UserService;
import com.noogoodd.api.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserService userService;
    private JwtUtil jwtUtil;

    private final List<AntPathRequestMatcher> excludeUrlPatterns = List.of(
            new AntPathRequestMatcher("/user/register"),
            new AntPathRequestMatcher("/user/login")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        for (AntPathRequestMatcher pathMatcher : excludeUrlPatterns) {
            if (pathMatcher.matches(request)) {
                chain.doFilter(request, response);
                return;
            }
        }

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDto userDto = userService.getUserByUsername(username);

            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(userDto.getUsername())
                    .password(userDto.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority(userDto.getRole())))
                    .build();

            if (jwtUtil.validateToken(jwt, userDetails)) {
                Long userId = jwtUtil.extractUserId(jwt);
                if (!userDto.getId().equals(userId)) {
                    throw new ServletException("User ID in token does not match the authenticated user");
                }

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}