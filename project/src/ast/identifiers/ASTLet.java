package ast.identifiers;

import ast.Exp;
import interpreter.Env;
import types.Type;

import java.util.HashMap;
import java.util.Map;

public class ASTLet  implements ast.Exp  {
    public Map<String, Exp> variables;
    public Exp body;
    public Type type;

    public ASTLet(Map<String, Exp> variables, Exp body) {
        this.body = body;
        this.variables = new HashMap<>();
        this.variables = variables;
    }

    public void addVariable(String variableName, Exp variableValue){
        variables.put(variableName, variableValue);
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
