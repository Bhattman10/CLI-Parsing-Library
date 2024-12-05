package oop.project.library.command;
import oop.project.library.parser.*;

public class Argument {

    public String name;
    public Parser<?> type;
    public Boolean optional;

    public Argument(Builder builder) {

        this.name = builder.name;
        this.type = builder.parser;
        this.optional = builder.optional;
    }

    public static class Builder {

        private String name;
        private Parser<?> parser;
        private Boolean optional;

        public static Builder newInstance() { return new Builder(); }
        private Builder() {}

        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder setType(Object type)
        {
            if(type == Integer.class || type == int.class)
            {
                this.parser = new IntegerParser();
            }
            else if(type == Double.class || type == double.class)
            {
                this.parser = new DoubleParser();
            }
            else if(type == String.class)
            {
                this.parser = new StringParser();
            }
            else if(type == Boolean.class || type == boolean.class)
            {
                this.parser = new BooleanParser();
            }

            return this;
        }

        public Builder setRange(int bottom, int top)
        {
            if(this.parser instanceof IntegerParser)
            {
                ((IntegerParser) this.parser).setRange(bottom, top);
            }
            else
            {
                throw new IllegalArgumentException("Invalid attempt to add range to type that is not an integer.");
            }

            return this;
        }

        //TODO

        public Builder setOptional(Boolean bool)
        {
            this.optional = bool;
            return this;
        }

        public Argument build()
        {
            return new Argument(this);
        }
    }
}
