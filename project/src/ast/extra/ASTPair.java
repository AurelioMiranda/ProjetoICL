package ast.extra;

import ast.Exp;
import interpreter.Env;

public class ASTPair implements ast.Exp {
    public Exp first;
    public Exp second;

    public ASTPair (Exp p1, Exp p2){
        this.first = p1;
        this.second = p2;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
