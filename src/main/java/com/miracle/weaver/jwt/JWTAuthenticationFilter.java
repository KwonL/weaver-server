package com.miracle.weaver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miracle.weaver.user.UserAdapter;
import com.sun.security.auth.UserPrincipal;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
        HttpServletResponse response) throws AuthenticationException {

        LoginDTO credentials = new ObjectMapper()
            .readValue(request.getInputStream(), LoginDTO.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            credentials.getUsername(),
            credentials.getPassword(),
            new ArrayList<>()
        );

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
        HttpServletResponse response, FilterChain chain, Authentication authResult) {
        UserAdapter userAdapter = (UserAdapter) authResult.getPrincipal();

        // Create JWT Token
        String token = JWT.create()
            .withSubject(userAdapter.getUsername())
            .withExpiresAt(new Date(System.currentTimeMillis() + JWTProperties.EXPIRATION_TIME))
            .sign(Algorithm.HMAC512(JWTProperties.SECRET.getBytes()));

        // Add token in response
        response.addHeader(JWTProperties.HEADER_STRING, JWTProperties.TOKEN_PREFIX + token);
    }
}