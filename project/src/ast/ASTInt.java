package ast;

import interpreter.Env;

public class ASTInt implements ast.Exp  {
    public int value;

    public ASTInt(int value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
