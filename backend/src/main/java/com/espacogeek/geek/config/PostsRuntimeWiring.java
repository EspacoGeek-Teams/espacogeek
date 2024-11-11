package com.espacogeek.geek.config;

import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.types.Scalars;

import graphql.schema.idl.RuntimeWiring;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostsRuntimeWiring implements RuntimeWiringConfigurer {

    @Override
    public void configure(@NonNull RuntimeWiring.Builder builder) {
        builder
            .scalar(Scalars.dateType())
            .build();
    }
}
