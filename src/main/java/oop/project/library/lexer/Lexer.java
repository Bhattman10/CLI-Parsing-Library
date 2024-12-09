package oop.project.library.lexer;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lexer {

    private final String arguments;
    private int positional_index = 0;
    private int char_index = 0;
    private final int string_length;

    private final List<String> positional_arguments = new ArrayList<>();
    private final Map<String, String> named_arguments = new HashMap<>();
    private final Map<String, Object> all_arguments = new HashMap<>();

    public Lexer(String input) throws ParseException {
        this.arguments = input;
        this.string_length = input.length();
        lex();
    }

    public List<String> get_positional_arguments()
    {
        return positional_arguments;
    }

    public Map<String, String> get_named_arguments()
    {
        return named_arguments;
    }

    public Map<String, Object> get_all_arguments()
    {
        return all_arguments;
    }

    private void lex() throws LexException
    {
        while(char_index < string_length)
        {
            if(Character.isWhitespace(arguments.charAt(char_index)))
            {
                continue;
            }
            else if(string_length - char_index > 3
                    && arguments.charAt(char_index) == '-'
                    && arguments.charAt(char_index+1) == '-'
                    && arguments.charAt(char_index+2) == '-')
            {
                throw new LexException("Invalid number of hyphens.");
            }
            else if(string_length - char_index > 2
                    && arguments.charAt(char_index) == '-'
                    && arguments.charAt(char_index+1) == '-')
            {
                char_index += 2;
                lexPositional();
                char_index++;
            }
            else
            {
                lexNamed();
                char_index++;
            }
        }
    }

    private void lexPositional() throws LexException
    {
        String flag_name = "", flag_value = "";

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            flag_name += arguments.charAt(char_index);
            char_index++;
        }

        if(flag_name.isEmpty())
        {
            throw new LexException("No flag name provided.");
        }

        //Iterate past white space
        char_index++;

        //Check if there are 2 hyphens, indicating a flag
        if(string_length - char_index > 2
                && arguments.charAt(char_index) == '-'
                && arguments.charAt(char_index+1) == '-')
        {
            throw new LexException("Flag used instead of value.");
        }

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            flag_value += arguments.charAt(char_index);
            char_index++;
        }

        if(flag_value.isEmpty())
        {
            throw new LexException("No flag value provided.");
        }

        named_arguments.put(flag_name, flag_value);
        all_arguments.put(flag_name, flag_value);
    }

    private void lexNamed()
    {
        String positional_argument = "";

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            positional_argument += arguments.charAt(char_index);
            char_index++;
        }

        positional_arguments.add(positional_argument);
        all_arguments.put(String.valueOf(positional_index), positional_argument);

        positional_index++;
    }
}
