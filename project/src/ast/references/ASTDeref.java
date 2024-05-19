package ast.references;
import ast.Exp;
import interpreter.Env;
public class ASTDeref implements Exp {
    public Exp expression;
    public ASTDeref(Exp expression){
        this.expression = expression;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {return v.visit(this);}
}
