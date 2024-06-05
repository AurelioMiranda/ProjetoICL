package ast.tuples;

import ast.Exp;
import interpreter.Env;

public class ASTFirst implements Exp {
    public Exp tuple;

    public ASTFirst (Exp tuple){
        this.tuple = tuple;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
