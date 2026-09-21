package com.jcooldevelopment.easybank_api.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// // Custom handler for exception 401 when user is not authenticated
// https://www.javaguides.net/2024/04/authenticationentrypoint-in-spring-security.html
// https://godwin-pinto.medium.com/spring-security-handle-unauthorized-and-unauthenticated-requests-75715e7fc579
public class AuthorizationExceptionHandler implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.getWriter().write("""
        {
            \"detail\": \"You need to log to access data.\",
            \"status\": 401,
            \"title\": \"User not authenticated\",
            \"type\": \"https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/401\"
        }
        """);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

}
