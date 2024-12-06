package oop.project.library.parser;

public class BooleanParser implements Parser<Boolean> {

    @Override
    public Boolean parse(String value) throws ParseException {

        if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(value);
        }
        else {
            throw new ParseException("Invalid boolean value: " + value);
        }
    }
}
