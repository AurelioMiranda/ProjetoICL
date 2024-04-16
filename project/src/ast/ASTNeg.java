package ast;

public class ASTNeg implements ast.Exp  {
    public boolean value;

    public ASTNeg(boolean value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
