package oop.project.library.parser;

public class IntegerParser implements Parser<Integer> {

    int topRange = Integer.MAX_VALUE;
    int bottomRange = Integer.MIN_VALUE;

    public void setRange(int bottomRange, int topRange) {
        this.bottomRange = bottomRange;
        this.topRange = topRange;
    }

    @Override
    public Integer parse(String value) throws ParseException {

        int parseResult;

        try {
            parseResult =  Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }

        if (parseResult < bottomRange || parseResult > topRange) {
            throw new ParseException("Integer value out of range.");
        }

        return parseResult;
    }
}
