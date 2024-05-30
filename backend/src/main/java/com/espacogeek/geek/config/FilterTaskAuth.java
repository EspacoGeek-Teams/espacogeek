package com.espacogeek.geek.config;

import java.io.IOException;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.espacogeek.geek.exception.GenericExeption;
import com.espacogeek.geek.services.User.UserService;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
/*
 * Filter if user is loged
 */
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
        var servletPath = request.getServletPath();
        if (!servletPath.endsWith("/auth")) {
            var authorization = request.getHeader("Authorization");
            if (authorization == null) {
                response.sendError(401);
                throw new GenericExeption(HttpStatus.UNAUTHORIZED.toString());
            }
    
            var authEncoded = authorization.substring(5).trim();
    
            byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
    
            var authString = new String(authDecoded);
    
            String[] credentials = authString.split(":");
    
            var user = this.userService.findByEmail(credentials[0]);
            if (user == null) {
                response.sendError(401);
                throw new GenericExeption(HttpStatus.UNAUTHORIZED.toString());
            } else {
                var resultPassword = BCrypt.verifyer().verify(credentials[1].toCharArray(), user.get().getPassword()).verified;
                if (resultPassword) {
                    request.setAttribute("idUser", user.get().getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                    throw new GenericExeption(HttpStatus.UNAUTHORIZED.toString());
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
