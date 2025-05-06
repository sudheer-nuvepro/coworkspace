package com.nuvepro.coworkspacebooking.security.filter;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nuvepro.coworkspacebooking.Bean.UserResponse;
import com.nuvepro.coworkspacebooking.Entity.UserAuthentication;
import com.nuvepro.coworkspacebooking.security.SecurityConstants;
import com.nuvepro.coworkspacebooking.security.manager.CustomAuthenticationManager;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private CustomAuthenticationManager authenticationManager;



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserAuthentication user = new ObjectMapper().readValue(request.getInputStream(), UserAuthentication.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json");
        String responseBody;
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        responseBody="{\n" +
                "    \"responseMessage\":\"Authentication failed\",\n" +
                "    \"responseStatus\":\"401 Unauthorized\",\n" +
                "}";
        var out = response.getOutputStream();
        out.write(responseBody.getBytes("UTF-8"));

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType("application/json");
        String responseBody;
        String token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        response.addHeader(SecurityConstants.AUTHORIZATION, SecurityConstants.BEARER + token);

        response.setStatus(HttpServletResponse.SC_OK);
        responseBody="{\n" +
                "    \"responseMessage\":\"Authentication successful\",\n" +
                "    \"responseStatus\":\"200 OK\",\n" +
                "    \"token\":\""+token+"\"\n" +
                "}";
        var out = response.getOutputStream();
        out.write(responseBody.getBytes("UTF-8"));

    }
}

