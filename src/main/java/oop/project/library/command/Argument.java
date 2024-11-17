package oop.project.library.command;

public class Argument {

    public String name;
    public Boolean named;
    public Object type;
    public Boolean optional;


    public Argument(Builder builder)
    {
        this.name = builder.name;
        this.named = builder.named;
        this.type = builder.type;
        this.optional = builder.optional;
    }

    // Static class Builder
    public static class Builder {

        // instance fields
        public String name;
        public Boolean named = false;
        public Object type;
        public Boolean optional = false;

        public static Builder newInstance()
        {
            return new Builder();
        }

        private Builder() {}

        // Setter methods
        public Builder setName(String name)
        {
            this.name = name;
            return this;
        }
        public Builder setNamed()
        {
            this.named = true;
            return this;
        }
        public Builder setType(Object type)
        {
            this.type = type;
            return this;
        }
        public Builder setRequired()
        {
            this.optional = true;
            return this;
        }

        // build method to deal with outer class
        // to return outer instance
        public Argument build()
        {
            return new Argument(this);
        }
    }
}
