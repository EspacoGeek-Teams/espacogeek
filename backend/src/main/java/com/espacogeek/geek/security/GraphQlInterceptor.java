package com.espacogeek.geek.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.server.WebGraphQlInterceptor;
import org.springframework.graphql.server.WebGraphQlRequest;
import org.springframework.graphql.server.WebGraphQlResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.services.UserService;
import com.espacogeek.geek.utils.DecodeBasicAuth;

import reactor.core.publisher.Mono;

/**
 * A Class to Intercept GraphQL Request and set session credentials
 */
@Component
public class GraphQlInterceptor implements WebGraphQlInterceptor {
    @Autowired
    private UserService userService;

    /**
     * This Method do the intercept to add the user credentials at session context to use anywhere in system.
     * @param request
     * @param chain 
     * @return Mono of WebGraphQlResponse
     */
    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        var authorization = request.getHeaders().getFirst("Authorization"); // * Pega a autorização do usuário 
        if (authorization == null) { // * Se nenhuma credenciais for inserida joga um erro
            throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
        }
        var user = userService.findByIdOrUsernameContainsOrEmail(null, null, new DecodeBasicAuth(authorization).getEmail()); // * Encontra o usuário com credenciais passada

        request.configureExecutionInput((executionInput, builder) -> builder.graphQLContext(Collections.singletonMap("userId", Integer.toString(user.get(0).get().getId()))).build()); // * Builda o contexto junto a informação que quero colocar no contexto
        return chain.next(request); // * retorna o contexto
    }
}
