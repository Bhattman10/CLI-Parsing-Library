package oop.project.library.command;

public class Argument {

    public String name;
    public Boolean named;
    public Object type;
    public Boolean optional;
    public int[] range;


    public Argument(Builder builder)
    {
        this.name = builder.name;
        this.named = builder.named;
        this.type = builder.type;
        this.optional = builder.optional;
        this.range = builder.range;
    }

    // Static class Builder
    //https://www.geeksforgeeks.org/builder-pattern-in-java/
    public static class Builder {

        public String name;
        public Boolean named = false;
        public Object type;
        public Boolean optional = false;
        public int[] range;

        public static Builder newInstance()
        {
            return new Builder();
        }

        private Builder() {}

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
        public Builder setRange(int bottom, int top)
        {
            this.range = new int[2];
            this.range[0] = bottom;
            this.range[1] = top;
            return this;
        }

        public Argument build()
        {
            return new Argument(this);
        }
    }
}
