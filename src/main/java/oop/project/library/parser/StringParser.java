package oop.project.library.parser;

public class StringParser implements Parser<String> {

    String[] choices;

    public void setChoices(String[] choices) {
        this.choices = choices;
    }

    @Override
    public String parse(String value) throws ParseException {

        if (choices == null || choices.length == 0) {
            return value;
        }

        for (String choice : choices) {
            if (choice.equals(value)) {
                return value;
            }
        }

        throw new ParseException("String value does not match provided choices.");
    }
}
