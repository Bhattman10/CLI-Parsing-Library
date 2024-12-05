package oop.project.library.parser;

public class IntegerParser implements Parser<Integer> {

    int topRange = 0;
    int bottomRange = 0;

    public void setRange(int topRange, int bottomRange) {
        this.topRange = topRange;
        this.bottomRange = bottomRange;
    }

    @Override
    public Integer parse(String value) throws ParseException {
        if(topRange == 0 && bottomRange == 0)
        {
            try {
                return Integer.parseInt(value);
            }
            catch (NumberFormatException e) {
                throw new ParseException(e.getMessage());
            }
        }
        else
        {
            int number = parse(value);
            if (number < bottomRange || number > topRange) {
                throw new ParseException("Integer value out of range.");
            }
            return number;
        }
    }

}
