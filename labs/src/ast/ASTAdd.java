package ast;

public class ASTAdd implements ast.Exp  {
    public Exp arg1;
    public Exp arg2;

    public ASTAdd(Exp arg1, Exp arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public <T> T accept(Exp.Visitor<T> v) {
        return v.visit(this);
    }
}
