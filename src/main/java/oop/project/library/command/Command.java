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

    public Map<String, Object> result = new HashMap<>();

    public Command(String name)
    {
        this.name = name;
    }

    public void addArgument(String name, Object type, Boolean required)
    {
        arguments.add(new Argument(name, type, required));
    }

    public Map<String, Object> parseArgs(String input) throws ParseException, Exception
    {
        lexer = new Lexer(input);

        //FIXME: only checks positional arguments
        if(lexer.get_positional_arguments().size() > arguments.size())
        {
            throw new Exception("Too many arguments");
        }

        for(int i = 0; i < arguments.size(); i++)
        {
            checkArgument(i);
        }
        return result;
    }

    //FIXME: Currently, only checks positional arguments. Develop solution for checking named too.
    private void checkArgument(int index) throws NumberFormatException
    {
        if(arguments.get(index).type == int.class)
        {
            String name = arguments.get(index).name;
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
}
