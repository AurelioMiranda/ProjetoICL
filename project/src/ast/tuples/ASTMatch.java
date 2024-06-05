package ast.tuples;

import ast.Exp;
import interpreter.Env;

import java.util.ArrayList;
import java.util.List;

public class ASTMatch implements ast.Exp { //TODO: continue
    public String tupleId;
    public Exp tuple;
    public List<String> tupleValues;

    public ASTMatch (String tupleId){
        this.tupleId = tupleId;
        this.tupleValues = new ArrayList<>();
    }

    public ASTMatch (Exp tuple){
        this.tuple = tuple;
        this.tupleValues = new ArrayList<>();
    }

    public void addValue (String e){
        tupleValues.add(e);
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}