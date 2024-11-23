package oop.project.library.parser;

public class StringParser implements Parser<String>
{
    @Override
    public String parse(String value) throws ParseException
    {
        return value;
    }

    public String parseChoices(String value, String[] choices) throws ParseException
    {
        for(String choice : choices)
        {
            if(value.equals(choice))
            {
                return value;
            }
        }

        throw new ParseException("Invalid choice");
    }
}
