package com.espacogeek.geek.types;

import graphql.schema.GraphQLScalarType;

public class Scalars {
    public static GraphQLScalarType dateType() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Date type")
                .coercing(new DateScalar())
                .build();
    }
}
