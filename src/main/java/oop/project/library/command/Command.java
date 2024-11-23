package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

    private Lexer lexer;
    private final List<Argument> arguments = new ArrayList<>();
    int positionalArgumentSize = 0, namedArgumentSize = 0;
    private final Map<String, Object> result = new HashMap<>();
    private int positionalIndex = 0;

    public Command() {}

    public void addArgument(Argument argument)
    {
        if(argument.named)
        {
            namedArgumentSize++;
        }
        else
        {
            positionalArgumentSize++;
        }

        arguments.add(argument);
    }

    public Map<String, Object> parseArgs(String input) throws Exception
    {
        lexer = new Lexer(input);

        if(lexer.get_positional_arguments().size() > positionalArgumentSize)
        {
            throw new Exception("Too many positional arguments");
        }
        else if(lexer.get_named_arguments().size() > namedArgumentSize)
        {
            throw new Exception("Too many named arguments");
        }

        for(int index = 0; index < arguments.size(); index++)
        {
            String argumentName = arguments.get(index).name;
            Object object = null;

            // Use default value upon mismatch
            if((arguments.get(index).named && arguments.get(index).default_value != null && !lexer.get_named_arguments().containsKey(argumentName))
                    || (!arguments.get(index).named && arguments.get(index).default_value != null && index == lexer.get_positional_arguments().size()))
            {
                object = arguments.get(index).default_value;
            }
            // Parse integer
            else if(arguments.get(index).type == int.class)
            {
                object = parseInteger(index, argumentName);
            }
            // Parse double
            else if(arguments.get(index).type == double.class)
            {
                object = parseDouble(index, argumentName);
            }
            // Parse string
            else if(arguments.get(index).type == String.class)
            {
                object = parseString(index, argumentName);
            }
            // Parse boolean
            else if(arguments.get(index).type == Boolean.class)
            {
                object = parseBoolean(index, argumentName);
            }
            // Parse custom
            else
            {
                object = parseCustom(index, argumentName);
            }

            result.put(argumentName, object);
        }

        return result;
    }

    private int parseInteger(int index, String argumentName)
    {
        IntegerParser intParser = new IntegerParser();
        int integer;

        //Parse named int arguments w/ ranges
        if(arguments.get(index).named && arguments.get(index).range != null)
        {
            integer = intParser.parseRange(lexer.get_named_arguments().get(argumentName),
                    arguments.get(index).range[0],
                    arguments.get(index).range[1]);
        }
        //Parse positional int arguments w/ ranges
        else if(arguments.get(index).range != null)
        {
            integer = intParser.parseRange(lexer.get_positional_arguments().get(positionalIndex++),
                    arguments.get(index).range[0],
                    arguments.get(index).range[1]);
        }
        //Parse named int arguments
        else if(arguments.get(index).named)
        {
            integer = intParser.parse(lexer.get_named_arguments().get(argumentName));
        }
        //Parse positional int arguments
        else
        {
            integer = intParser.parse(lexer.get_positional_arguments().get(positionalIndex++));
        }

        return integer;
    }

    private double parseDouble(int index, String argumentName) throws NumberFormatException
    {
        DoubleParser doubleParser = new DoubleParser();
        double doubleNumber;

        if(arguments.get(index).named)
        {
            doubleNumber = doubleParser.parse(lexer.get_named_arguments().get(argumentName));
        }
        else
        {
            doubleNumber = doubleParser.parse(lexer.get_positional_arguments().get(positionalIndex++));
        }

        return doubleNumber;
    }

    private String parseString(int index, String argumentName) throws Exception
    {
        StringParser stringParser = new StringParser();
        String string;

        //Parse named string arguments w/ choices
        if(arguments.get(index).named && arguments.get(index).choices != null)
        {
            string = stringParser.parseChoices(lexer.get_named_arguments().get(argumentName), arguments.get(index).choices);
        }
        //Parse positional string arguments w/ choices
        else if(arguments.get(index).choices != null)
        {
            string = stringParser.parseChoices(lexer.get_positional_arguments().get(positionalIndex++), arguments.get(index).choices);
        }
        //Parse named string arguments
        else if(arguments.get(index).named)
        {
            string = stringParser.parse(lexer.get_named_arguments().get(argumentName));
        }
        //Parse positional string arguments
        else
        {
            string = stringParser.parse(lexer.get_positional_arguments().get(positionalIndex++));
        }

        return string;
    }

    private Boolean parseBoolean(int index, String argumentName) throws Exception
    {
        BooleanParser booleanParser = new BooleanParser();
        boolean bool;

        //Parse named boolean arguments
        if(arguments.get(index).named)
        {
            bool = booleanParser.parse(lexer.get_named_arguments().get(argumentName));
        }
        //Parse positional boolean arguments
        else
        {
            bool = booleanParser.parse(lexer.get_positional_arguments().get(positionalIndex++));
        }

        return bool;
    }

    private Object parseCustom(int index, String argumentName) throws Exception
    {
        //Parses positional arguments with their custom parsers
        return Parser.useParser(arguments.get(index).customParser, lexer.get_positional_arguments().get(positionalIndex++));
    }
}
