package ast;

import interpreter.Env;

public class ASTNew implements Exp {
    public String varName;
    public Exp expression;

    public ASTNew(String varName, Exp expression) {
        this.expression = expression;
        this.varName = varName;
    }

    public ASTNew(String varName) {
        this.varName = varName;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
