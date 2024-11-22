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

            // Parse integer
            if(arguments.get(index).type == int.class && arguments.get(index).range == null)
            {
                object = parseInteger(index, argumentName);
            }
            // Parse integer w/ range
            else if(arguments.get(index).type == int.class)
            {
                object = parseIntegerRange(index, argumentName);
            }
            // Parse double
            else if(arguments.get(index).type == double.class)
            {
                object = parseDouble(index, argumentName);
            }
            // Parse string choices
            else if(arguments.get(index).choices != null)
            {
                object = parseStringChoices(index, argumentName);
            }

            //TODO

            result.put(argumentName, object);
        }

        return result;
    }

    private int parseInteger(int index, String argumentName) throws NumberFormatException
    {
        int integer;

        if(arguments.get(index).named)
        {
            integer = parser.parseInt(lexer.get_named_arguments().get(argumentName));
        }
        else
        {
            integer = parser.parseInt(lexer.get_positional_arguments().get(index));
        }

        return integer;
    }

    private int parseIntegerRange(int index, String argumentName) throws NumberFormatException
    {
        int integer;

        if(arguments.get(index).named)
        {
            integer = parser.parseIntRange(lexer.get_named_arguments().get(argumentName),
                    arguments.get(index).range[0],
                    arguments.get(index).range[1]);
        }
        else
        {
            integer = parser.parseIntRange(lexer.get_positional_arguments().get(index),
                    arguments.get(index).range[0],
                    arguments.get(index).range[1]);
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

    private String parseStringChoices(int index, String argumentName) throws Exception
    {
        String choice;

        if(arguments.get(index).named)
        {
            choice = parser.parseStringChoices(lexer.get_named_arguments().get(argumentName), arguments.get(index).choices);
        }
        else
        {
            choice = parser.parseStringChoices(lexer.get_positional_arguments().get(index), arguments.get(index).choices);
        }

        return choice;
    }
}
