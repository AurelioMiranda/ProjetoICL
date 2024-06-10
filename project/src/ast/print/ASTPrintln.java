package ast.print;

import ast.Exp;
import interpreter.Env;
import types.Type;

public class ASTPrintln implements Exp {
    public Exp exp;
    public Type type;

    public ASTPrintln (Exp exp){
        this.exp = exp;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }

    public void assignType(Type t) {
        this.type = t;
    }
}
