package ast.extra;

import ast.Exp;
import interpreter.Env;

public class ASTSecond implements Exp {
    public Exp tuple;

    public ASTSecond (Exp tuple){
        this.tuple = tuple;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
