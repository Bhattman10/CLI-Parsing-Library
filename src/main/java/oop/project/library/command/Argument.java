package oop.project.library.command;
import oop.project.library.lexer.LexException;
import oop.project.library.parser.*;

public class Argument {

    public String name;
    public Parser<?> type;
    public Object optional;

    public Argument(Builder builder) {

        this.name = builder.name;
        this.type = builder.parser;
        this.optional = builder.optional;
    }

    /**
     * After creating the command object, we can build arguments within addArgument using the following structure:
     * Argument.Builder.newInstance()
     *                 .setName("...")
     *                 .setType("...".class)
     *                 ...
     *                 ...
     *                 .build());
     */
    public static class Builder {

        private String name;
        private Object type;
        private Parser<?> parser;
        private Object optional;

        public static Builder newInstance() { return new Builder(); }
        private Builder() {}

        /**
         * First required builder method! Provides your argument with a name. You can classify your arguments as named by including two hyphens at
         * the beginning, or as positional by simply entering a normal string name.
         */
        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        /**
         * Second required builder method! Provides your argument with a type. Depending on the type selected, a parser will automatically be selected.
         * To assign a type, enter the type followed by ".class". If a custom type is used, no parser will be assigned.
         */
        public Builder setType(Object type)
        {
            if(type == Integer.class || type == int.class)
            {
                this.type = type;
                this.parser = new IntegerParser();
            }
            else if(type == Double.class || type == double.class)
            {
                this.type = type;
                this.parser = new DoubleParser();
            }
            else if(type == String.class)
            {
                this.type = type;
                this.parser = new StringParser();
            }
            else if(type == Boolean.class || type == boolean.class)
            {
                this.type = type;
                this.parser = new BooleanParser();
            }
            else
            {
                this.type = type;
            }

            return this;
        }

        /**
         * Allows setting a custom range for integer types utilizing the default integer parser.
         * To enter a range, pass in a lower and upper integer value. Ranges are inclusive.
         * Ranges can only be used with the default integer parser.
         */
        public Builder setRange(int bottom, int top)
        {
            if(this.parser instanceof IntegerParser)
            {
                ((IntegerParser) this.parser).setRange(bottom, top);
            }
            else
            {
                throw new ArgumentException("Cannot add range to type of class " + this.parser.getClass() + "." +
                        "Ranges can only be used with default integer parser.");
            }

            return this;
        }

        /**
         * Allows setting custom choices for string types utilizing the default string parser.
         * To provide choices, pass in an array of strings.
         * Choices can only be used with the default string parser.
         */
        public Builder setChoices(String[] choices)
        {
            if(this.parser instanceof StringParser)
            {
                ((StringParser) this.parser).setChoices(choices);
            }
            else
            {
                throw new ArgumentException("Cannot add choices to type of class " + this.parser.getClass() + "." +
                        "Choices can only be used with default string parser.");
            }

            return this;
        }

        /**
         * Arguments can be set as optional, provided a default value is given.
         * To set an argument as optional, simply pass in the value you wish the argument to default to. This can be any object type.
         * However, the value provided must be of the same type as the argument.
         * Positional arguments can only be optional if they're after all other required arguments.
         */
        public Builder setOptional(Object value)
        {
            if (value.getClass() != this.type)
                throw new ArgumentException("The type of default value must be of type " + this.type + ". " +
                        "The value type provided is " + value.getClass() + ".");

            this.optional = value;
            return this;
        }

        /**
         * Allows you to utilize any parser in the argument. Custom parsers can be used with any type.
         */
        public Builder setCustomParser(Parser<?> parser)
        {
            this.parser = parser;
            return this;
        }

        /**
         * Final require builder method! Validates argument fields prior to building the argument and sending it to command.
         */
        public Argument build()
        {
            if(this.name == null)
                throw new ArgumentException("No argument name provided.");
            else if(this.parser == null)
                throw new ArgumentException("Argument cannot be used because parser is null. Remember to set a type;" +
                        "if your type is custom, use setCustomParser.");

            return new Argument(this);
        }
    }
}
