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

    public void addArgument(String name, Boolean positional, Object type, Boolean required)
    {
        arguments.add(new Argument(name, positional, type, required));
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
            if(arguments.get(index).positional)
            {
                parsePositionalArgument(index);
            }
            else
            {
                parseNamedArgument(index);
            }
        }

        return result;
    }

    private void parsePositionalArgument(int index) throws NumberFormatException
    {
        String name = arguments.get(index).name;

        if(arguments.get(index).type == int.class)
        {
            int integer = parser.parseInt(lexer.get_positional_arguments().get(index));
            result.put(name, integer);
        }
        else if(arguments.get(index).type == double.class)
        {
            parser.parseInt(lexer.get_positional_arguments().get(index));
        }
        else if(arguments.get(index).type == boolean.class)
        {
            parser.parseInt(lexer.get_positional_arguments().get(index));
        }
        else if(arguments.get(index).type == String.class)
        {
            parser.parseInt(lexer.get_positional_arguments().get(index));
        }
        else
        {
            //TODO: custom parser
        }

    }

    private void parseNamedArgument(int index) throws NumberFormatException
    {
        String name = arguments.get(index).name;

        if(arguments.get(index).type == int.class)
        {
            int number = parser.parseInt(lexer.get_named_arguments().get(name));
            result.put(name, number);
        }
        else if(arguments.get(index).type == double.class)
        {
            double number = parser.parseDouble(lexer.get_named_arguments().get(name));
            result.put(name, number);
        }
        else if(arguments.get(index).type == boolean.class)
        {
            parser.parseInt(lexer.get_named_arguments().get(name));
        }
        else if(arguments.get(index).type == String.class)
        {
            parser.parseInt(lexer.get_named_arguments().get(name));
        }
        else
        {
            //TODO: custom parser
        }

    }
}
