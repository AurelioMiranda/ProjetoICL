package ast.logical;

import ast.Exp;
import interpreter.Env;

public class ASTAnd implements ast.Exp{
    public Exp e1;
    public Exp e2;

    public ASTAnd(Exp e1, Exp e2){
        this.e1 = e1;
        this.e2 = e2;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
