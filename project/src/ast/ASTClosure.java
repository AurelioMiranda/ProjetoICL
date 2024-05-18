package ast;

import interpreter.Env;
import values.Value;

import java.util.List;

public class ASTClosure implements ast.Exp { //let f = fun () -> 3 in f()
    public Exp code;
    public List<ASTParameter> params; //can be 0 (no args)
    public Env<Value> environment;


    public ASTClosure (List<ASTParameter> params, Exp code){
        this.params = params;
        this.code = code;
        this.environment = new Env<>();
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
    // fun (id1 : t1) (id2 : t2) : t3 -> e
    // fun (x : bool) -> x+x

    /*let x = 1 in
    let f = fun y -> x + y in
            f 3
    let x = 2 in
    f 4*/
}
