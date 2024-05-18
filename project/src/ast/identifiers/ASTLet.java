package ast.identifiers;

import ast.Exp;
import interpreter.Env;
import types.Type;

public class ASTLet  implements ast.Exp  { //TODO: identifiers
    public String variableName; //Initially List<String> variableName but parser doesnt accept Arraylists so... idk
    public Exp variableValue;
    public Exp body;
    public Type type;

    public ASTLet(String variableName, Exp variableValue, Exp body) {
        this.variableName = variableName;
        this.variableValue = variableValue;
        this.body = body;
    }

    public ASTLet (){ }

    public void addPair(String id, Exp value){
        this.variableName = id;
        this.variableValue = value;
    }

    public void addType (String type){

    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
