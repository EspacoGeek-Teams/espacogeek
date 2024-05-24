package com.espacogeek.geek.exception.resolver;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.exception.GenericExeption;

@Component
public class GenericExeptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable exception, DataFetchingEnvironment env) {
        if (exception instanceof GenericExeption) {
            return GraphqlErrorBuilder.newError(env)
                    .message(exception.getMessage())
                    .build();
        }
        return null;
    }
}