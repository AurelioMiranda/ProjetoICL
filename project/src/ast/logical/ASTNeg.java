package ast.logical;

import ast.Exp;
import interpreter.Env;

public class ASTNeg implements ast.Exp  {
    public Exp e;

    public ASTNeg(Exp e) {
        this.e = e;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
