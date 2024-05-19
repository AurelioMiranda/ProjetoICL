package ast.references;

import ast.Exp;
import interpreter.Env;

public class ASTNew implements Exp {
    //public Exp expression;
    public Exp expression;

    public ASTNew ( Exp expression){
        this.expression = expression;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {return v.visit(this);}
}
