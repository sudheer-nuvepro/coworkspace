package com.nuvepro.coworkspacebooking.security.filter;

import java.io.IOException;


import com.nuvepro.coworkspacebooking.exception.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.exceptions.JWTVerificationException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json");
        String responseBody;

        try {
            filterChain.doFilter(request, response); //go to next filter in SecurityConfig class
        } catch (EntityNotFoundException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
             responseBody="{\n" +
                    "    \"responseMessage\":\"Username doesn't exist\",\n" +
                    "    \"responseStatus\":\"404 Not Found\",\n" +
                    "}";
            var out = response.getOutputStream();
            out.write(responseBody.getBytes("UTF-8"));

        } catch (JWTVerificationException e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            responseBody="{\n" +
                    "    \"responseMessage\":\"JWT NOT VALID\",\n" +
                    "    \"responseStatus\":\"403 Forbidden\",\n" +
                    "}";
            var out = response.getOutputStream();
            out.write(responseBody.getBytes("UTF-8"));

        } catch (RuntimeException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            responseBody="{\n" +
                    "    \"responseMessage\":\" BadREQUEST\",\n" +
                    "    \"responseStatus\":\"400 Bad Request\",\n" +
                    "}";
            var out = response.getOutputStream();
            out.write(responseBody.getBytes("UTF-8"));

        }
    }
}

