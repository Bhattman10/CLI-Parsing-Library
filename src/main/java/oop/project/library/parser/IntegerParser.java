package oop.project.library.parser;

public class IntegerParser implements Parser<Integer>
{
    @Override
    public Integer parse(String value) throws ParseException
    {
        try
        {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e)
        {
            throw new ParseException(e.getMessage());
        }
    }

    public Integer parseRange(String value, int bottom, int top) throws ParseException
    {
        int number = parse(value);
        if(number < bottom || number > top)
        {
            throw new ParseException("Value out of range");
        }
        return number;
    }
}
