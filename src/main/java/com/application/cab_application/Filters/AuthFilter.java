package com.application.cab_application.Filters;

import com.application.cab_application.Util.CurrentUserHelper;
import com.application.cab_application.Util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthFilter extends HttpFilter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
        if (path.equals("/accounts") || path.equals("/driver_login") || path.equals("/logout") || path.equals("/rider_login") || path.equals("/api/login")) {
            chain.doFilter(request, response);
        } else {
            HttpSession httpSession = httpServletRequest.getSession(false);
            if (httpSession != null && httpSession.getAttribute("userID") != null) {
                CurrentUserHelper.account = (int) httpSession.getAttribute("userID");
                chain.doFilter(request, response);
                return;
            }

            String token = httpServletRequest.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                boolean tokenValidation = JWTUtil.verifyAuthToken(token);
                if (tokenValidation) {
                    CurrentUserHelper.account = JWTUtil.getUserID(token);
                    chain.doFilter(request, response);
                    return;
                } else {
                    ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "InValid Token");
                }
            }


            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }
}
