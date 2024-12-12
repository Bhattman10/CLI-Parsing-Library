package oop.project.library.parser;

public class DoubleParser implements Parser<Double> {

    double topRange = Double.POSITIVE_INFINITY;
    double bottomRange = Double.NEGATIVE_INFINITY;

    public void setRange(double bottomRange, double topRange) {
        this.bottomRange = bottomRange;
        this.topRange = topRange;
    }

    @Override
    public Double parse(String value) throws ParseException {

        double parseResult;

        try {
            parseResult =  Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            throw new ParseException(e.getMessage());
        }

        if (parseResult < bottomRange || parseResult > topRange) {
            throw new ParseException("Double value out of range.");
        }

        return parseResult;
    }
}

