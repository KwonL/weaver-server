package com.miracle.weaver.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.miracle.weaver.user.User;
import com.miracle.weaver.user.UserAdapter;
import com.miracle.weaver.user.UserRepository;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
        UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWTProperties.HEADER_STRING);

        if (header == null || !header.startsWith(JWTProperties.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(JWTProperties.HEADER_STRING);
        if (token != null) {
            String username = JWT.require(Algorithm.HMAC512(JWTProperties.SECRET.getBytes()))
                .build()
                .verify(token.replace(JWTProperties.TOKEN_PREFIX, ""))
                .getSubject();

            if (username != null) {
                Optional<User> opt_user = userRepository.findByUsername(username);
                if (opt_user.isPresent()) {
                    User user = opt_user.get();
                    UserAdapter userAdapter = new UserAdapter(user.getUsername(),
                        user.getPassword(), user.isAdmin());
                    return new UsernamePasswordAuthenticationToken(
                        username, null, userAdapter.getAuthorities());
                }
            }
        }
        return null;
    }
}
