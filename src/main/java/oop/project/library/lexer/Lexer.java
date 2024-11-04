package oop.project.library.lexer;

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

    public Map<String, Object> lex() throws Exception
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
                //TODO: throw error
            }
            else if(string_length - char_index > 2
                    && arguments.charAt(char_index) == '-'
                    && arguments.charAt(char_index+1) == '-')
            {
                char_index += 2;
                lexPositional();
            }
            else
            {
                lexNamed();
            }
        }

        return lexed_arguments;
    }

    private void lexPositional() throws Exception
    {
        String flag_name = "", flag_value = "";

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            flag_name += arguments.charAt(char_index);
            char_index++;
        }

        if(flag_name == "")
        {
            throw new Exception("No flag name provided.");
        }

        char_index++;

        while(char_index < string_length && !Character.isWhitespace(arguments.charAt(char_index)))
        {
            flag_value += arguments.charAt(char_index);
            char_index++;
        }

        if(flag_value == "")
        {
            throw new Exception("No flag value provided.");
        }

        lexed_arguments.put(flag_name, flag_value);
    }

    private void lexNamed() throws Exception
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
