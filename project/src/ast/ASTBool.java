package ast;

public class ASTBool implements ast.Exp {
    public boolean value;

    public ASTBool(boolean value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Exp.Visitor<T> v) {
        return v.visit(this);
    }
}
