package oop.project.library.command;

public class Argument {

    public String name;
    public Boolean named;
    public Object type;
    public int[] range;
    public String[] choices;


    public Argument(Builder builder)
    {
        this.name = builder.name;
        this.named = builder.named;
        this.type = builder.type;
        this.range = builder.range;
        this.choices = builder.choices;
    }

    // Static class Builder
    //https://www.geeksforgeeks.org/builder-pattern-in-java/
    public static class Builder {

        public String name;
        public Boolean named;
        public Object type;
        public int[] range;
        public String[] choices;

        public static Builder newInstance()
        {
            return new Builder();
        }

        private Builder() {}

        public Builder setName(String name)
        {
            if(name.startsWith("--"))
            {
                this.name = name.substring(2);
                this.named = true;
            }
            else
            {
                this.name = name;
                this.named = false;
            }
            return this;
        }
        public Builder setType(Object type)
        {
            this.type = type;
            return this;
        }
        public Builder setRange(int bottom, int top)
        {
            this.range = new int[2];
            this.range[0] = bottom;
            this.range[1] = top;
            return this;
        }
        public Builder setChoices(String[] choices)
        {
            this.choices = choices;
            return this;
        }

        public Argument build()
        {
            return new Argument(this);
        }
    }
}
