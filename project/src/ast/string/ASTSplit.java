package ast.string;

import ast.Exp;
import interpreter.Env;

public class ASTSplit implements ast.Exp  {
    public Exp arg1;
    public Exp arg2;

    public ASTSplit(Exp arg1, Exp arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}