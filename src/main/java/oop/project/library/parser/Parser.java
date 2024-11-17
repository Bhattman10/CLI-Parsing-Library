package oop.project.library.parser;

import java.util.HashMap;
import java.util.Map;

public class Parser {
    public interface CustomParser<T> {
        T parse(String input);
    }

    private final Map<Class<?>, CustomParser<?>> customParsers = new HashMap<>();

    public Parser() {}

    public int parseInt(String input) throws NumberFormatException {
        return Integer.parseInt(input);
    }

    public int parseIntRange(String input, int bottom, int top) throws NumberFormatException {
        int number = Integer.parseInt(input);
        if(number >= bottom && number <= top)
        {
            return number;
        }

        throw new NumberFormatException();
    }

    public double parseDouble(String input) throws NumberFormatException {
        return Double.parseDouble(input);
    }

    public boolean parseBoolean(String input) {
        if ("true".equalsIgnoreCase(input)) {
            return true;
        } else if ("false".equalsIgnoreCase(input)) {
            return false;
        } else {
            throw new IllegalArgumentException("Invalid boolean value: " + input);
        }
    }

    public String parseString(String input) {
        return input;
    }

    public <T> void registerCustomParser(Class<T> type, CustomParser<T> parser) {
        customParsers.put(type, parser);
    }

    // Parser for custom types
    @SuppressWarnings("unchecked")
    public <T> T parseCustom(String input, Class<T> type) {
        if (!customParsers.containsKey(type)) {
            throw new IllegalArgumentException("No custom parser registered for type: " + type.getSimpleName());
        }
        return (T) customParsers.get(type).parse(input);
    }
}
