package ast.print;

import ast.Exp;
import interpreter.Env;
import types.Type;

public class ASTPrint implements Exp {
    public Exp exp;
    public Type type;

    public ASTPrint (Exp exp){
        this.exp = exp;
    }

    public void assignType (Type t){
        this.type = t;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
