package ast.control_flow;

import ast.Exp;
import interpreter.Env;

public class ASTWhile implements ast.Exp {
    public Exp condition;
    public Exp body;

    public ASTWhile(Exp condition, Exp body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
