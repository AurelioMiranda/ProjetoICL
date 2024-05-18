package ast;

import interpreter.Env;

public class ASTAssign implements Exp {

    public Exp leftHandSide;
    public Exp rightHandSide;

    public ASTAssign(Exp leftHandSide, Exp rightHandSide) {
        this.leftHandSide = leftHandSide;
        this.rightHandSide = rightHandSide;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}