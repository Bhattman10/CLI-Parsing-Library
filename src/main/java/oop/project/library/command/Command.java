package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

    private Lexer lexer;
    private final Parser parser = new Parser();
    private final List<Argument> arguments = new ArrayList<>();
    private final Map<String, Object> result = new HashMap<>();

    public Command() {}

    public void addArgument(Argument argument)
    {
        arguments.add(argument);
    }

    public Map<String, Object> parseArgs(String input) throws Exception
    {
        lexer = new Lexer(input);

        if(lexer.get_all_arguments().size() > arguments.size())
        {
            throw new Exception("Too many arguments");
        }

        for(int index = 0; index < arguments.size(); index++)
        {
            String argumentName = arguments.get(index).name;
            Object object = null;

            // Use default value upon mismatch
            if((arguments.get(index).named && arguments.get(index).default_value != null && !lexer.get_named_arguments().containsKey(argumentName))
                    || (arguments.get(index).default_value != null && index >= lexer.get_positional_arguments().size()))
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

            //TODO

            result.put(argumentName, object);
        }

        return result;
    }

    private int parseInteger(int index, String argumentName) throws NumberFormatException
    {
        int integer;

        //Parse named int arguments w/ ranges
        if(arguments.get(index).named && arguments.get(index).range != null)
        {
            integer = parser.parseIntRange(lexer.get_named_arguments().get(argumentName),
                    arguments.get(index).range[0],
                    arguments.get(index).range[1]);
        }
        //Parse positional int arguments w/ ranges
        else if(arguments.get(index).range != null)
        {
            integer = parser.parseIntRange(lexer.get_positional_arguments().get(index),
                    arguments.get(index).range[0],
                    arguments.get(index).range[1]);
        }
        //Parse named int arguments
        else if(arguments.get(index).named)
        {
            integer = parser.parseInt(lexer.get_named_arguments().get(argumentName));
        }
        //Parse positional int arguments
        else
        {
            integer = parser.parseInt(lexer.get_positional_arguments().get(index));
        }

        return integer;
    }

    private double parseDouble(int index, String argumentName) throws NumberFormatException
    {
        double number;

        if(arguments.get(index).named)
        {
            number = parser.parseDouble(lexer.get_named_arguments().get(argumentName));
        }
        else
        {
            number = parser.parseDouble(lexer.get_positional_arguments().get(index));
        }

        return number;
    }

    private String parseString(int index, String argumentName) throws Exception
    {
        String choice;

        //Parse named string arguments w/ choices
        if(arguments.get(index).named && arguments.get(index).choices != null)
        {
            choice = parser.parseStringChoices(lexer.get_named_arguments().get(argumentName), arguments.get(index).choices);
        }
        //Parse positional string arguments w/ choices
        else if(arguments.get(index).choices != null)
        {
            choice = parser.parseStringChoices(lexer.get_positional_arguments().get(index), arguments.get(index).choices);
        }
        //Parse named string arguments
        if(arguments.get(index).named)
        {
            choice = parser.parseString(lexer.get_named_arguments().get(argumentName));
        }
        //Parse positional string arguments
        else
        {
            choice = parser.parseString(lexer.get_positional_arguments().get(index));
        }

        return choice;
    }
}
