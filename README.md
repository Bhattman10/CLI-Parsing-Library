# CLI Parsing Library
Designed with Java software developers in-mind, the CLI Parser provides an easy-to-use set of instructions to parse Command Line arguments.
This program provides a filter between the user and application, discarding all arguments that do not fit input requirements while organizing the proper arguments with their types and values.
The inspiration behind this project is the motivation to provide developers a simple way to make their projects more secure and bug-proof. Catching improper argument states
and data types at the front-door is the first step.
## Getting Started
1. Download the library package.
2. In your project directroy, place the library in a designated space for storing and retrieving commands from the CLI.
3. Follow the instructions below for customizing arguments in Scenarios.java utilizing Builder methods.
## Building Arguments
### Initial Parsing of Argument Titles
The bulk of argument customization occurs in Scenarios.java. The **parse** method contains all starting argument names, which when fed by the CLI will flow into the specific method for that argument. For now, do not worry about implementing the argument method, but fill in the argument name along with the future method's name.
```
case "lex" -> lex(arguments);
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "fizzbuzz" -> fizzbuzz(arguments);
            case "difficulty" -> difficulty(arguments);
            case "echo" -> echo(arguments);
            case "search" -> search(arguments);
            case "weekday" -> weekday(arguments);
```
### Basic Anatomy of an Argument
Now that you have your argument titles, we can begin filling in argument features. CLI arguments always have two fields: 1) named, 2) positional. Here is an example of an argument with positional and named arguments, listed respectively.
```
argument 1 --flag "hello"
```
This argument contains one positional integer argument, along with a named argument (or flag) indicating the string "hello".
### Initialize an Argument Method
To get started with building the argument, we first need to initialize the method within Scenarios.java. As seen above, argument methods have the same name as the argument.
```
private static Result<Map<String, Object>> argument(String arguments) {}
```
Argument methods return a Map<String, Object> of argument field names to argument field values. Upon succesful parsing of an argument, this map will be passed back to the default Main method. You can customize functionality of argument results here, however for now let's get started building this new argument class.
### Command Object
Frist, create a new command object.
```
Command command = new Command();
```
From here, we can add a field name, field type and finally close the field off. The following is an example of building an argument field that only accepts integer. This would be considered a positional argument.
```
command.addArgument(Argument.Builder.newInstance()
                .setName("positional")
                .setType(Integer.class)
                .build());
```
Positional arguments are parsed as their name implies. If the parser expects the first field to be an integer, it needs to be an integer. However, the name "positional" is not checked for, as positional arguments do not require users to pass in named like flags.
Next, let's build a named argument that only accepts Strings. Remeber, a named argument contains two hyphens.
```
command.addArgument(Argument.Builder.newInstance()
                .setName("--flag")
                .setType(String.class)
                .build());
```
We are nearly done with this argument. Now we will add the final boilerplate code required for this method.
```
try
        {
            var parsed = command.parseArgs(arguments);
            int positional = (int) parsed.get("positional");
            String flag = (String) parsed.get("flag");
            return new Result.Success<>(Map.of("positional", 1, "flag", "hello"));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
```
This final piece of code will remain mostly the same aside for 3 lines. The first line in the try block will always exist, however lines 2-4 can be customized according to the specifications of thr argument. Everything else remains the same for all argument functions. Here is the argument method in whole:
```
private static Result<Map<String, Object>> argument(String arguments) {

        Command command = new Command();
        command.addArgument(Argument.Builder.newInstance()
                .setName("positional")
                .setType(Integer.class)
                .build());
        command.addArgument(Argument.Builder.newInstance()
                .setName("--flag")
                .setType(String.class)
                .build());

        try
        {
            var parsed = command.parseArgs(arguments);
            int positional = (int) parsed.get("positional");
            String flag = (String) parsed.get("flag");
            return new Result.Success<>(Map.of("positional", 1, "flag", "hello"));
        }
        catch (Exception e)
        {
            return new Result.Failure<>(e.getMessage());
        }
    }
```
Now, when you run your CLI program and a user passes in argument 1 --flag "hello", this will pass the parser and you can continue forward with functionality for "argument" using the map results passed back to Main.














