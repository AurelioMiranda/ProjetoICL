package ast.identifiers;

import interpreter.Env;

public class ASTIdentifier implements ast.Exp {
    private String name;

    public ASTIdentifier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }

}