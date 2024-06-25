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

        var servletPath = request.getServletPath(); // * @AbigailGeovanaPega a url
        if (!servletPath.endsWith("/auth/")) { // * @AbigailGeovanaSó entra (filtra) as url que não teermina em /auth/. Todos os caminhos que não estão especificado aqui é filtrado se o usuário existe ou não
            var authorization = request.getHeader("Authorization"); // * @AbigailGeovanaPega o Basic Auth Base64
            if (authorization == null) { // * @AbigailGeovanaSe não for inserido nenhuma credenciais
                throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
            }

            var decodeBasicAuth = new DecodeBasicAuth(authorization); // * @AbigailGeovanaDesencripta a credencial
            var user = userService.findByIdOrUsernameContainsOrEmail(null, null, decodeBasicAuth.getEmail()).getFirst(); // * @AbigailGeovanaEncontra o usuário

            if (!user.isPresent()) { // * @AbigailGeovanaSe o usuário for encontrado não da erro
                throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
            } else {
                var resultPassword = BCrypt.verifyer().verify(decodeBasicAuth.getPassword().toCharArray(),user.get().getPassword()).verified; // * @AbigailGeovanaVerifica se a senha é validada
                if (resultPassword) {
                    filterChain.doFilter(request, response); // * @AbigailGeovanaSe  valida permite requisição
                } else {
                    throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
                }
            }
        } else {
            filterChain.doFilter(request, response); // * @AbigailGeovanaSe URL terminal em /auth/ permite sem filtrar
        }
    }
}
