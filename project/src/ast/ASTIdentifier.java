package ast;

public class ASTIdentifier implements ast.Exp {
    private String name;
    private Integer number; // Allow for representing numbers as identifiers

    public ASTIdentifier(String name) {
        this.name = name;
        this.number = null; // Initialize number as null
    }

    public ASTIdentifier(Integer number) {
        this.name = null; // Initialize name as null
        this.number = number;
    }

    // Getters for name and number
    public String getName() {
        return name;
    }

    public Integer getNumber() {
        return number;
    }

    // Check if the identifier represents a string
    public boolean isStringIdentifier() {
        return name != null;
    }

    // Check if the identifier represents a number
    public boolean isNumberIdentifier() {
        return number != null;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }

}