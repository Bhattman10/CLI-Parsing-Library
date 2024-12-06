package oop.project.library.scenarios;

import oop.project.library.command.Argument;
import oop.project.library.command.Command;
import oop.project.library.lexer.Lexer;
import oop.project.library.parser.IntegerParser;
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

        Command command = new Command();
        command.addArgument(Argument.Builder.newInstance()
                .setName("left")
                .setType(Integer.class)
                .build());
        command.addArgument(Argument.Builder.newInstance()
                .setName("right")
                .setType(Integer.class)
                .build());

        try
        {
            var parsed = command.parseArgs(arguments);
            int left = (int) parsed.get("left");
            int right = (int) parsed.get("right");
            return new Result.Success<>(Map.of("left", left, "right", right));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> sub(String arguments) {

        Command command = new Command();
        command.addArgument(Argument.Builder.newInstance()
                .setName("--left")
                .setType(Double.class)
                .build());
        command.addArgument(Argument.Builder.newInstance()
                .setName("--right")
                .setType(Double.class)
                .build());

        try
        {
            var parsed = command.parseArgs(arguments);
            double left = (double) parsed.get("left");
            double right = (double) parsed.get("right");
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

        Command command = new Command();
        command.addArgument(Argument.Builder.newInstance()
                .setName("number")
                .setType(Integer.class)
                .setRange(1, 100)
                .build());

        try
        {
            var parsed = command.parseArgs(arguments);
            int number = (int) parsed.get("number");
            return new Result.Success<>(Map.of("number", number));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> difficulty(String arguments) {

        Command command = new Command();
        command.addArgument(Argument.Builder.newInstance()
                .setName("difficulty")
                .setType(String.class)
                .setChoices(new String[]{"easy", "normal", "hard", "peaceful"})
                .build());

        try
        {
            var parsed = command.parseArgs(arguments);
            String difficulty = (String) parsed.get("difficulty");
            return new Result.Success<>(Map.of("difficulty", difficulty));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> echo(String arguments) {

        Command command = new Command();
        command.addArgument(Argument.Builder.newInstance()
                .setName("message")
                .setType(String.class)
                .setOptional("Echo, echo, echo...")
                .build());

        try
        {
            var parsed = command.parseArgs(arguments);
            String message = (String) parsed.get("message");
            return new Result.Success<>(Map.of("message", message));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    //TODO
    private static Result<Map<String, Object>> search(String arguments) {

        Command command = new Command();
        command.addArgument(Argument.Builder.newInstance()
                .setName("term")
                .setType(String.class)
                .build());
        command.addArgument(Argument.Builder.newInstance()
                .setName("--case-insensitive")
                .setType(Boolean.class)
                .setOptional(false)
                .build());

        try
        {
            var parsed = command.parseArgs(arguments);
            String term = (String) parsed.get("term");
            Boolean caseInsensitive = (Boolean) parsed.get("case-insensitive");
            return new Result.Success<>(Map.of("term", term, "case-insensitive", caseInsensitive));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }

    private static Result<Map<String, Object>> weekday(String arguments) {

        Command command = new Command();
        command.addArgument(Argument.Builder.newInstance()
                .setName("date")
                .setType(LocalDate.class)
                .setCustomParser(LocalDate::parse)
                .build());

        try
        {
            var parsed = command.parseArgs(arguments);
            LocalDate date = (LocalDate) parsed.get("date");
            return new Result.Success<>(Map.of("date", date));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }
}
