package ast.print;

import ast.Exp;
import interpreter.Env;

public class ASTPrintln implements Exp {
    public Exp exp;

    public ASTPrintln (Exp exp){
        this.exp = exp;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
