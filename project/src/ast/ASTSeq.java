package ast;

import ast.Exp;
import interpreter.Env;

public class ASTSeq implements Exp {
    public Exp first;
    public Exp second;

    public ASTSeq(Exp first, Exp second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}