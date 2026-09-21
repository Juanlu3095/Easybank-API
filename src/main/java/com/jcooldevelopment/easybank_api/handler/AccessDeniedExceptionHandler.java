package com.jcooldevelopment.easybank_api.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Custom handler for exception 403 when authenticated user has no permissions to access
// https://www.javaguides.net/2024/04/authenticationentrypoint-in-spring-security.html
// https://godwin-pinto.medium.com/spring-security-handle-unauthorized-and-unauthenticated-requests-75715e7fc579
public class AccessDeniedExceptionHandler implements AccessDeniedHandler{

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
          
        response.getWriter().write("""
        {
            \"detail\": \"User has no permissions to access.\",
            \"status\": 403,
            \"title\": \"Access denied\",
            \"type\": \"https://developer.mozilla.org/en-US/docs/Web/HTTP/Reference/Status/403\"
        }
        """);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }

}
