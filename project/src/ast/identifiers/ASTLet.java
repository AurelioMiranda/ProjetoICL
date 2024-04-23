package ast.identifiers;

import ast.Exp;
import interpreter.Env;

public class ASTLet  implements ast.Exp  { //TODO: identifiers
    public String variableName; //Initially List<String> variableName but parser doesnt accept Arraylists so... idk
    public Exp variableValue;
    public Exp body;

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
