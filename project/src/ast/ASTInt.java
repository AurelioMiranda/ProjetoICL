package ast;

public class ASTInt implements ast.Exp  { //TODO: Minus has conflicts (-3)*6
    public int value;

    public ASTInt(int value) {
        this.value = value;
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
