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

@Component
public class GraphQlInterceptor implements WebGraphQlInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public Mono<WebGraphQlResponse> intercept(WebGraphQlRequest request, Chain chain) {
        var authorization = request.getHeaders().getFirst("Authorization");
        if (authorization == null) {
            throw new GenericException(HttpStatus.UNAUTHORIZED.toString());
        }
        var user = userService.findByIdOrUsernameContainsOrEmail(null, null, new DecodeBasicAuth(authorization).getEmail());

        request.configureExecutionInput((executionInput, builder) -> builder.graphQLContext(Collections.singletonMap("userId", Integer.toString(user.get(0).get().getId()))).build());
        return chain.next(request);
    }
}
