package oop.project.library.parser;

import java.text.ParseException;

@FunctionalInterface
public interface Parser<T>
{
    T parse(String value) throws ParseException;

    static <T> T useParser(Parser<T> parser, String value) throws ParseException
    {
        return parser.parse(value);
    }
}