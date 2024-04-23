package ast.control_flow;

import ast.Exp;
import interpreter.Env;

public class ASTIf implements ast.Exp {
    public Exp condition;
    public Exp body;

    public ASTIf(Exp condition, Exp body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
