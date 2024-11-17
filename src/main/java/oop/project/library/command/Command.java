package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

    private String name;
    private Lexer lexer;
    private Parser parser = new Parser();
    private List<Argument> arguments = new ArrayList<>();
    private int positional_index;
    private Map<String, Object> result = new HashMap<>();

    public Command(String name)
    {
        this.name = name;
    }

    public void addArgument(Argument argument)
    {
        arguments.add(argument);
    }

    public Map<String, Object> parseArgs(String input) throws ParseException, Exception
    {
        lexer = new Lexer(input);

        if(lexer.get_all_arguments().size() > arguments.size())
        {
            throw new Exception("Too many arguments");
        }

        for(int index = 0; index < arguments.size(); index++)
        {
            if(arguments.get(index).named)
            {
                parseNamedArgument(index);
            }
            else
            {
                parsePositionalArgument(index);
            }
        }

        return result;
    }

    private void parsePositionalArgument(int index) throws NumberFormatException
    {
        String name = arguments.get(index).name;

        //Parse integer
        if(arguments.get(index).type == int.class && arguments.get(index).range == null)
        {
            int integer = parser.parseInt(lexer.get_positional_arguments().get(index));
            result.put(name, integer);
        }
        //Parse integer w/ range
        if(arguments.get(index).type == int.class)
        {
            int integer = parser.parseIntRange(lexer.get_positional_arguments().get(index),
                    arguments.get(index).range[0],
                    arguments.get(index).range[1]);
            result.put(name, integer);
        }
        //Parse double
        else if(arguments.get(index).type == double.class)
        {
            double number = parser.parseDouble(lexer.get_positional_arguments().get(index));
            result.put(name, number);
        }
        //Parse boolean
        else if(arguments.get(index).type == boolean.class)
        {
            //TODO
        }
        //Parse string
        else if(arguments.get(index).type == String.class)
        {
            //TODO
        }
        //Parse custom type
        else
        {
            //TODO
        }
    }

    private void parseNamedArgument(int index) throws NumberFormatException
    {
        String name = arguments.get(index).name;

        //Parse integer
        if(arguments.get(index).type == int.class && arguments.get(index).range == null)
        {
            int integer = parser.parseInt(lexer.get_named_arguments().get(name));
            result.put(name, integer);
        }
        //Parse integer w/ range
        if(arguments.get(index).type == int.class)
        {
            int integer = parser.parseIntRange(lexer.get_named_arguments().get(name),
                    arguments.get(index).range[0],
                    arguments.get(index).range[1]);
            result.put(name, integer);
        }
        //Parse double
        else if(arguments.get(index).type == double.class)
        {
            double number = parser.parseDouble(lexer.get_named_arguments().get(name));
            result.put(name, number);
        }
        //Parse boolean
        else if(arguments.get(index).type == boolean.class)
        {
            //TODO
        }
        //Parse string
        else if(arguments.get(index).type == String.class)
        {
            //TODO
        }
        //Parse custom type
        else
        {
            //TODO
        }
    }
}
