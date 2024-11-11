package com.espacogeek.geek.types;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class DateScalar implements Coercing<Date, String> {
    @Override
    public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
        if (dataFetcherResult instanceof Date) {
            return ((Date) dataFetcherResult).toString();
        } else {
            throw new CoercingSerializeException("Not a valid DateTime");
        }
    }

    @Override
    public Date parseValue(Object input) throws CoercingParseValueException {
        try {
            return new SimpleDateFormat(DateTimeFormatter.ISO_DATE_TIME.toString(), Locale.ENGLISH).parse(input.toString());
        } catch (ParseException e) {
            throw new CoercingSerializeException("Not a valid Date");
        }
    }

    @Override
    public Date parseLiteral(Object input) throws CoercingParseLiteralException {
        try {
            if (input instanceof StringValue) return new SimpleDateFormat(DateTimeFormatter.ISO_DATE_TIME.toString(), Locale.ENGLISH).parse(input.toString());
        } catch (ParseException e) {
            throw new CoercingSerializeException("Not a valid Date");
        }

        throw new CoercingParseLiteralException("Value is not a valid ISO date time");
    }
}
