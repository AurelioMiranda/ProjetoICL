package ast;

import interpreter.Env;

public class ASTBool implements ast.Exp {
    public boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
