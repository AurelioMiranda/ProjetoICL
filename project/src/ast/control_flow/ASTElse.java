package ast.control_flow;

import ast.Exp;
import interpreter.Env;

public class ASTElse implements ast.Exp {
    public Exp condition;
    public Exp ifBody;
    public Exp elseBody;

    public ASTElse(Exp condition, Exp ifBody, Exp elseBody) {
        this.condition = condition;
        this.ifBody = ifBody;
        this.elseBody = elseBody;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
