package oop.project.library.command;

public class Argument {

    public String name;
    public Object type;
    public Boolean required;

    public Argument(String name, Object type, Boolean required)
    {
        this.name = name;
        this.type = type;
        this.required = required;
    }
}
