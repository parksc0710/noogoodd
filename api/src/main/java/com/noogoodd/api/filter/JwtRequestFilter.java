package com.noogoodd.api.filter;

import com.noogoodd.api.user.application.dto.UserDto;
import com.noogoodd.api.user.application.service.UserService;
import com.noogoodd.api.user.domain.User;
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

        Long userId = null;
        String userEmail = "";
        String userTpye = "";
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            userId = jwtUtil.extractUserId(jwt);
            userEmail = jwtUtil.extractUserEmail(jwt);
            userTpye = jwtUtil.extractUserType(jwt);
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            User userDetails = userService.getUserServiceByUserEmail(userEmail, userTpye);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                if (!userDetails.getId().equals(userId)) {
                    throw new ServletException("User ID in token does not match the authenticated user");
                }

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }
        }
        chain.doFilter(request, response);
    }
}