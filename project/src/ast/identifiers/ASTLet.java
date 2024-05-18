package ast.identifiers;

import ast.Exp;
import interpreter.Env;
import types.Type;

public class ASTLet  implements ast.Exp  {
    public String variableName; //TODO: list of variables and types
    public Exp variableValue;
    public Exp body;
    public Type type;

    public ASTLet(String variableName, Exp variableValue, Exp body) {
        this.variableName = variableName;
        this.variableValue = variableValue;
        this.body = body;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
