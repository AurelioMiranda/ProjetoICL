package ast;

public class ASTNew implements Exp {
    private Exp expression; // Expression representing the value to be stored

    public ASTNew(Exp expression) {
        this.expression = expression;
    }

    public Exp getExpression() {
        return expression;
    }

    @Override
    public String toString() {
        return "NEW(" + expression.toString() + ")";
    }

    @Override
    public <T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
