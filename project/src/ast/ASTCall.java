package ast;

import interpreter.Env;

public class ASTCall implements ast.Exp {
    public Exp identifier;
    public Exp arguments;

    public ASTCall(Exp identifier, Exp arguments) {
        this.identifier = identifier;
        this.arguments = arguments;
    }

    public ASTCall(Exp identifier) {
        this.identifier = identifier;
    }

    public void addArg (Exp arg2){
        this.arguments = arg2;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
