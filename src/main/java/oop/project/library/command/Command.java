package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Command {

    private final List<Argument> arguments = new ArrayList<>();
    int positionalArgumentSize = 0, namedArgumentSize = 0;
    private int positionalIndex = 0;

    public Command() {}

    public void addArgument(Argument argument)
    {
        if(argument.name.startsWith("--"))
        {
            namedArgumentSize++;
        }
        else
        {
            positionalArgumentSize++;
        }

        arguments.add(argument);
    }

    //TODO
    public Map<String, Object> parseArgs(String input) throws java.text.ParseException {

        Lexer lexer = new Lexer(input);
        Map<String, Object> result = new HashMap<>();

        if(lexer.get_positional_arguments().size() > positionalArgumentSize)
        {
            throw new ParseException("Too many positional arguments");
        }
        else if(lexer.get_named_arguments().size() > namedArgumentSize)
        {
            throw new ParseException("Too many named arguments");
        }

        for (Argument argument : arguments) {

            if(argument.name.startsWith("--"))
            {
                result.put(argument.name, Parser.useParser(argument.type, lexer.get_named_arguments().get(argument.name.substring(1))));
            }
            else
            {
                result.put(argument.name, Parser.useParser(argument.type, lexer.get_positional_arguments().get(positionalIndex)));
                positionalIndex++;
            }
        }

        return result;
    }

}
