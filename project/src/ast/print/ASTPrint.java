package ast.print;

import ast.Exp;
import interpreter.Env;

public class ASTPrint implements Exp {
    public Exp exp;

    public ASTPrint (Exp exp){
        this.exp = exp;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
