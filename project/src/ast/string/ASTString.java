package ast.string;

import interpreter.Env;

public class ASTString implements ast.Exp  {
    public String value;

    public ASTString(String value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
