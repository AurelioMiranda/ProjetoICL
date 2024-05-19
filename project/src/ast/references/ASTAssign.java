package ast.references;

import ast.Exp;
import interpreter.Env;
public class ASTAssign implements Exp {
    public Exp lhs;
    public Exp rhs;

    public ASTAssign(Exp lhs, Exp rhs){
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Exp getLhs() { return lhs;}

    public Exp getRhs() { return rhs;}

    @Override
    public <T> T accept(Visitor<T, Env<T>> v){
        return v.visit(this);
    }


}
