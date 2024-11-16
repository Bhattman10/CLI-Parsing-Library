package oop.project.library.command;

public class Argument {

    public String name;
    public Boolean positional;
    public Object type;
    public Boolean required;

    public Argument(String name, Boolean positional, Object type, Boolean required)
    {
        this.name = name;
        this.positional = positional;
        this.type = type;
        this.required = required;
    }
}
