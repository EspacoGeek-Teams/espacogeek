package com.espacogeek.geek.exception.resolver;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.exception.GenericException;

@Component
public class GenericExceptionResolver extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(@NonNull Throwable exception, @NonNull DataFetchingEnvironment env) {
        if (exception instanceof GenericException) {
            return GraphqlErrorBuilder.newError(env)
                    .message(exception.getMessage())
                    .build();
        }
        return null;
    }
}
