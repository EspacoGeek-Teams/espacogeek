package com.espacogeek.geek.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolver;

import com.espacogeek.geek.exception.resolver.GenericExceptionResolver;

@Configuration
public class GraphQLConfig {

    public DataFetcherExceptionResolver customGraphQLExceptionResolver() {
        return new GenericExceptionResolver();
    }
}
