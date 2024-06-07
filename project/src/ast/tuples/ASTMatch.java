package ast.tuples;

import ast.Exp;
import interpreter.Env;

import java.util.ArrayList;
import java.util.List;

public class ASTMatch implements ast.Exp {
    public String tupleId;
    public Exp tuple;
    public List<String> tupleIds;
    public Exp body;

    public ASTMatch (String tupleId){
        this.tupleId = tupleId;
        this.tupleIds = new ArrayList<>();
    }

    public ASTMatch (Exp tuple){
        this.tuple = tuple;
        this.tupleIds = new ArrayList<>();
    }

    public void addValue (String e){
        tupleIds.add(e);
    }

    public void addBody (Exp e){
        this.body = e;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}