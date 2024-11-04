package oop.project.library.lexer;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class Lexer {

    public String arguments;
    public Map<String, Object> lexed_arguments = new HashMap<>();
    public int positional_index = 0;
    public int char_index = 0;
    public int string_length;

    public Lexer(String input) {
        this.arguments = input;
        this.string_length = input.length();
    }

    public Map<String, Object> lex() throws ParseException
    {
        if(char_index == string_length)
        {
            throw new ParseException("No arguments provided.", char_index);
        }

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

        return lexed_arguments;
    }

    private void lexPositional() throws ParseException
    {
        String flag_name = "", flag_value = "";

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            flag_name += arguments.charAt(char_index);
            char_index++;
        }

        if(flag_name == "")
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

        if(flag_value == "")
        {
            throw new ParseException("No flag value provided.", char_index);
        }

        lexed_arguments.put(flag_name, flag_value);
    }

    private void lexNamed()
    {
        String positional_argument = "";

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            positional_argument += arguments.charAt(char_index);
            char_index++;
        }

        lexed_arguments.put(String.valueOf(positional_index), positional_argument);

        positional_index++;
    }
}
