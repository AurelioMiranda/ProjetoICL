package ast;

public class ASTInt implements ast.Exp  {
    public int value;

    public ASTInt(int value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
