package ast.tuples;

import ast.Exp;
import interpreter.Env;

import java.util.ArrayList;
import java.util.List;

public class ASTPair implements ast.Exp {
    public List<Exp> tupleList;

    public ASTPair (Exp p1){
        tupleList = new ArrayList<>();
        tupleList.add(p1);
    }

    public void addValue (Exp e){
        tupleList.add(e);
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
