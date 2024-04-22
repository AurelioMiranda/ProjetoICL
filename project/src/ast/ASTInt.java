package ast;

import interpreter.Env;

public class ASTInt implements ast.Exp  { //TODO: Minus has conflicts (-3)*6
    public int value;

    public ASTInt(int value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
