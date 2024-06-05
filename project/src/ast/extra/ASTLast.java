package ast.extra;

import ast.Exp;
import interpreter.Env;

public class ASTLast implements Exp {
    public Exp tuple;

    public ASTLast (Exp tuple){
        this.tuple = tuple;
    }

    //match x with
    //| x, y, z ->
    @Override
    public <T> T accept(Exp.Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
