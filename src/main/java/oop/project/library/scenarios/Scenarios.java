package oop.project.library.scenarios;

import oop.project.library.command.Argument;
import oop.project.library.command.Command;
import oop.project.library.lexer.Lexer;
import oop.project.library.parser.Parser;

import java.time.LocalDate;
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

        try
        {
            Lexer lexer = new Lexer(arguments);

            if(lexer.get_all_arguments().isEmpty())
            {
                throw new Exception("No arguments provided");
            }

            return new Result.Success<>(lexer.get_all_arguments());
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

        Command command = new Command("add");
        command.addArgument(Argument.Builder.newInstance()
                .setName("left")
                .setType(int.class)
                .build());
        command.addArgument(Argument.Builder.newInstance()
                .setName("right")
                .setType(int.class)
                .build());

        try
        {
            return new Result.Success<>(command.parseArgs(arguments));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> sub(String arguments) {

        Command command = new Command("sub");
        command.addArgument(Argument.Builder.newInstance()
                .setName("left")
                .setNamed()
                .setType(double.class)
                .build());
        command.addArgument(Argument.Builder.newInstance()
                .setName("right")
                .setNamed()
                .setType(double.class)
                .build());

        try
        {
            return new Result.Success<>(command.parseArgs(arguments));
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

        Command command = new Command("fizzbuzz");
        command.addArgument(Argument.Builder.newInstance()
                .setName("number")
                .setType(int.class)
                .setRange(1, 100)
                .build());

        try
        {
            return new Result.Success<>(command.parseArgs(arguments));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    //TODO
    private static Result<Map<String, Object>> difficulty(String arguments) {

        try
        {
            Lexer lexer = new Lexer(arguments);
            Parser parser = new Parser();

            if(lexer.get_positional_arguments().size() != 1)
            {
                throw new Exception("Invalid number of positional arguments.");
            }

            String difficulty = parser.parseString(lexer.get_positional_arguments().getFirst());

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

    //TODO
    private static Result<Map<String, Object>> echo(String arguments) {

        try
        {
            Lexer lexer = new Lexer(arguments);
            Parser parser = new Parser();
            String message;

            if(lexer.get_named_arguments().size() > 1)
            {
                throw new Exception("Too many named arguments.");
            }

            if(lexer.get_positional_arguments().size() > 1)
            {
                throw new Exception("Too many positional arguments.");
            }

            // Argument is a positional argument
            if(lexer.get_positional_arguments().size() == 1)
            {
                message = parser.parseString(lexer.get_positional_arguments().getFirst());
            }
            // Argument is a named argument
            else
            {
                message = parser.parseString(lexer.get_named_arguments().get("message"));
            }

            return new Result.Success<>(Map.of("message", Objects.requireNonNullElse(message, "Echo, echo, echo...")));

        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    //TODO
    private static Result<Map<String, Object>> search(String arguments) {

        try
        {
            Lexer lexer = new Lexer(arguments);
            Parser parser = new Parser();

            if(lexer.get_positional_arguments().size() != 1)
            {
                throw new Exception("Invalid number of positional arguments.");
            }

            if(lexer.get_named_arguments().size() > 1)
            {
                throw new Exception("Too many named arguments.");
            }

            String term = parser.parseString(lexer.get_positional_arguments().getFirst());

            if(lexer.get_named_arguments().containsKey("case-insensitive"))
            {
                Boolean flag = parser.parseBoolean(lexer.get_named_arguments().get("case-insensitive"));
                return new Result.Success<>(Map.of("term", term, "case-insensitive", flag));
            }

            return new Result.Success<>(Map.of("term", term, "case-insensitive", false));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    //TODO
    private static Result<Map<String, Object>> weekday(String arguments) {
        try
        {
            Lexer lexer = new Lexer(arguments);
            Parser parser = new Parser();

            if(lexer.get_positional_arguments().size() != 1)
            {
                throw new Exception("Invalid number of positional arguments.");
            }

            parser.registerCustomParser(LocalDate.class, LocalDate::parse);

            LocalDate date = parser.parseCustom(lexer.get_positional_arguments().getFirst(), LocalDate.class);

            return new Result.Success<>(Map.of("date", date));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

}
