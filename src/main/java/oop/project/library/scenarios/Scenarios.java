package oop.project.library.scenarios;

import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.text.ParseException;
import java.util.Map;
import java.util.Objects;

public class Scenarios {

    public static Result<Map<String, Object>> parse(String command) {
        //Note: Unlike argparse4j, our library will contain a lexer than can
        //support an arbitrary String (instead of requiring a String[] array).
        //We still need to split the base command from the actual arguments
        //string to know which scenario (aka command) we're trying to parse
        //arguments for. This sounds like something a library should handle...
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "lex" -> lex(arguments);
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "fizzbuzz" -> fizzbuzz(arguments);
            case "difficulty" -> difficulty(arguments);
            case "echo" -> echo(arguments);
            case "search" -> search(arguments);
            case "weekday" -> weekday(arguments);
            default -> throw new AssertionError("Undefined command " + base + ".");
        };
    }

    private static Result<Map<String, Object>> lex(String arguments) {
        //Note: For ease of testing, this should use your Lexer implementation
        //directly rather and return those values.

        Lexer lexer = new Lexer(arguments);

        try
        {
            Map<String, Object> result = lexer.lex();
            return new Result.Success<>(result);
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> add(String arguments) {
        //Note: For this part of the project, we're focused on lexing/parsing.
        //The implementation of these scenarios isn't going to look like a full
        //command, but rather some weird hodge-podge mix. For example:
        //var args = Lexer.parse(arguments);
        //var left = IntegerParser.parse(args.positional[0]);
        //This is fine - our goal right now is to implement this functionality
        //so we can build up the actual command system in Part 3.

        Lexer lexer = new Lexer(arguments);
        Parser parser = new Parser();

        try
        {
            Map<String, Object> result = lexer.lex();

            if(result.size() != 2)
            {
                throw new Exception("Invalid number of arguments.");
            }

            int left = parser.parseInt((String) result.get("0"));
            int right = parser.parseInt((String) result.get("1"));

            return new Result.Success<>(Map.of("left", left, "right", right));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> sub(String arguments) {
        Lexer lexer = new Lexer(arguments);
        Parser parser = new Parser();

        try
        {
            Map<String, Object> result = lexer.lex();

            if(result.size() != 2)
            {
                throw new Exception("Invalid number of arguments.");
            }

            double left = parser.parseDouble((String) result.get("left"));
            double right = parser.parseDouble((String) result.get("right"));

            return new Result.Success<>(Map.of("left", left, "right", right));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> fizzbuzz(String arguments) {
        //Note: This is the first command your library may not support all the
        //functionality to implement yet. This is fine - parse the number like
        //normal, then check the range manually. The goal is to get a feel for
        //the validation involved even if it's not in the library yet.
        //var number = IntegerParser.parse(lexedArguments.get("number"));
        //if (number < 1 || number > 100) ...

        Lexer lexer = new Lexer(arguments);
        Parser parser = new Parser();

        try
        {
            Map<String, Object> result = lexer.lex();

            if(result.size() != 1)
            {
                throw new Exception("Invalid number of arguments.");
            }

            int number = parser.parseInt((String) result.get("0"));

            if(number < 1 || number > 100)
            {
                throw new Exception("Invalid range.");
            }

            return new Result.Success<>(Map.of("number", number));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {

        Lexer lexer = new Lexer(arguments);
        Parser parser = new Parser();

        try
        {
            Map<String, Object> result = lexer.lex();

            if(result.size() != 1)
            {
                throw new Exception("Invalid number of arguments.");
            }

            String difficulty = parser.parseString((String) result.get("0"));

            if(!Objects.equals(difficulty, "easy")
                    && !Objects.equals(difficulty, "normal")
                    && !Objects.equals(difficulty, "hard")
                    && !Objects.equals(difficulty, "peaceful"))
            {
                throw new Exception("Invalid difficulty mode.");
            }

            return new Result.Success<>(Map.of("difficulty", difficulty));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> echo(String arguments) {

        Lexer lexer = new Lexer(arguments);
        Parser parser = new Parser();

        try
        {
            Map<String, Object> result = lexer.lex();

            if(result.size() > 1)
            {
                throw new Exception("Invalid number of arguments.");
            }

            String message = parser.parseString((String) result.get("0"));

            return new Result.Success<>(Map.of("message", message));
        }
        catch (Exception e)
        {
            if(e.getMessage().equals("No arguments provided."))
            {
                return new Result.Success<>(Map.of("message", "Echo, echo, echo!"));
            }

            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> search(String arguments) {
        throw new UnsupportedOperationException("TODO"); //TODO
    }

    private static Result<Map<String, Object>> weekday(String arguments) {
        throw new UnsupportedOperationException("TODO"); //TODO
    }

}
