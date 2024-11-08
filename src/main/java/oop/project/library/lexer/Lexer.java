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

    public List<String> positional_arguments = new ArrayList<>();
    public Map<String, String> named_arguments = new HashMap<>();
    public Map<String, Object> all_arguments = new HashMap<>();

    public Lexer(String input) throws ParseException {
        this.arguments = input;
        this.string_length = input.length();
        lex();
    }

    private void lex() throws ParseException
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
                throw new ParseException("Invalid number of hyphens.", char_index);
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

    private void lexPositional() throws ParseException
    {
        String flag_name = "", flag_value = "";

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            flag_name += arguments.charAt(char_index);
            char_index++;
        }

        if(flag_name.isEmpty())
        {
            throw new ParseException("No flag name provided.", char_index);
        }

        //Iterate past white space
        char_index++;

        //Check if there are 2 hyphens, indicating a flag
        if(string_length - char_index > 2
                && arguments.charAt(char_index) == '-'
                && arguments.charAt(char_index+1) == '-')
        {
            throw new ParseException("Flag used instead of value.", char_index);
        }

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            flag_value += arguments.charAt(char_index);
            char_index++;
        }

        if(flag_value.isEmpty())
        {
            throw new ParseException("No flag value provided.", char_index);
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
