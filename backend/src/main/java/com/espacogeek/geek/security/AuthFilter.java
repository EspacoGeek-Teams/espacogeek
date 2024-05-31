package com.espacogeek.geek.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.services.UserService;
import com.espacogeek.geek.utils.DecodeBasicAuth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath();
        if (!servletPath.endsWith("/auth/")) {
            var authorization = request.getHeader("Authorization");
            if (authorization == null) {
                throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
            }

            var decodeBasicAuth = new DecodeBasicAuth(authorization);
            var user = userService.findByIdOrUsernameContainsOrEmail(null, null, decodeBasicAuth.getEmail());

            if (!user.get(0).isPresent()) {
                throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
            } else {
                var resultPassword = BCrypt.verifyer().verify(decodeBasicAuth.getPassword().toCharArray(),user.get(0).get().getPassword()).verified;
                if (resultPassword) {
                    filterChain.doFilter(request, response);
                } else {
                    throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
