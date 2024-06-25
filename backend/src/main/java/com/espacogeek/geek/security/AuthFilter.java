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

/**
 * A Class to filter all request to API. It also handle with authentication by now.
 */
@Component
@Deprecated
public class AuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;

    @Override
    /**
    * AuthFilter Handler. It only permit pass request to /auth/
    */
    @Deprecated
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var servletPath = request.getServletPath(); // * Pega a url
        if (!servletPath.endsWith("/auth/")) { // * Só entra (filtra) as url que não teermina em /auth/. Todos os caminhos que não estão especificado aqui é filtrado se o usuário existe ou não
            var authorization = request.getHeader("Authorization"); // * Pega o Basic Auth Base64
            if (authorization == null) { // * Se não for inserido nenhuma credenciais
                throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
            }

            var decodeBasicAuth = new DecodeBasicAuth(authorization); // * Desencripta a credencial
            var user = userService.findByIdOrUsernameContainsOrEmail(null, null, decodeBasicAuth.getEmail()).getFirst(); // * Encontra o usuário

            if (!user.isPresent()) { // * Se o usuário for encontrado não da erro
                throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
            } else {
                var resultPassword = BCrypt.verifyer().verify(decodeBasicAuth.getPassword().toCharArray(),user.get().getPassword()).verified; // * Verifica se a senha é validada
                if (resultPassword) {
                    filterChain.doFilter(request, response); // * Se  valida permite requisição
                } else {
                    throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
                }
            }
        } else {
            filterChain.doFilter(request, response); // * Se URL terminal em /auth/ permite sem filtrar
        }
    }
}
