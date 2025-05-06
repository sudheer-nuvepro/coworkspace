package com.nuvepro.coworkspacebooking.security.filter;

import java.io.IOException;
import java.util.Arrays;

import com.nuvepro.coworkspacebooking.security.SecurityConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");//header contains Bearer JWT

        if (header == null || !header.startsWith(SecurityConstants.BEARER)) //means there is no jwt token to authorize means user is in register page
        {
            filterChain.doFilter(request, response); //since it is last filter in chain, it causes the resource at the end of the chain to be invoked i.e register method in UserController classs
            return;
        }

        String token = header.replace(SecurityConstants.BEARER, ""); /// we dont want Bearer in header
        String user = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET_KEY))
                .build()
                .verify(token)
                .getSubject();

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, Arrays.asList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);  // go to next filter in SecurityConfig class
    }
}
