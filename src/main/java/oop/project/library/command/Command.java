package oop.project.library.command;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.*;
import java.util.*;

public class Command {

    private final List<Argument> arguments = new ArrayList<>();
    private final Set<String> argumentNames = new HashSet<>();
    private final Map<String, Object> result = new HashMap<>();
    private int totalNumberLexedArguments = 0;
    private int positionalIndex = 0;

    /**
     * The first step in creating an argument is to initialize a command object.
     */
    public Command() {}

    /**
     * Secondly, use addArgument to pass in arguments. You may pass in as many valid argument as needed.
     * Reference the argument builder method notes on how to construct an argument and pass it into this method.
     */
    public void addArgument(Argument argument)
    {
        if (!argumentNames.add(argument.name)) {
            throw new ArgumentException("Duplicate argument: " + argument.name);
        }

        arguments.add(argument);
    }

    /**
     * Finally, call this method to lex arguments and compare the given CLI arguments with the ones you created.
     */
    public Map<String, Object> parseArgs(String input) throws java.text.ParseException {

        Lexer lexer = new Lexer(input);
        totalNumberLexedArguments = lexer.get_named_arguments().size() + lexer.get_positional_arguments().size();

        for (Argument argument : arguments) {

            if(argument.name.startsWith("--"))
            {
                parseNamedArguments(lexer, argument);
            }
            else
            {
                parsePositionalArguments(lexer, argument);
            }
        }

        if (totalNumberLexedArguments != 0)
            throw new ArgumentException(totalNumberLexedArguments + " lexed arguments remaining.");

        return result;
    }

    private void parseNamedArguments(Lexer lexer, Argument argument) throws java.text.ParseException {
        try {

            String valueToParse = lexer.get_named_arguments().get(argument.name.substring(2));
            result.put(argument.name.substring(2), Parser.useParser(argument.type, valueToParse));
            totalNumberLexedArguments--;

        } catch (Exception e) {

            if (argument.optional == null)
            {
                throw new ArgumentException("No argument passed in for " + argument.name);
            }
            else
            {
                String valueToParse = argument.optional.toString();
                result.put(argument.name.substring(2), Parser.useParser(argument.type, valueToParse));
            }
        }
    }

    private void parsePositionalArguments(Lexer lexer, Argument argument) throws java.text.ParseException {
        try {

            String valueToParse = lexer.get_positional_arguments().get(positionalIndex);
            result.put(argument.name, Parser.useParser(argument.type, valueToParse));
            totalNumberLexedArguments--;
            positionalIndex++;

        } catch (Exception e) {

            if (argument.optional == null) {

                throw new ArgumentException("No argument passed in for " + argument.name);

            } else {

                String valueToParse = argument.optional.toString();
                result.put(argument.name, Parser.useParser(argument.type, valueToParse));
            }
        }
    }
}
