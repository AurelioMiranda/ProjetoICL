package ast;

import interpreter.Env;
import values.Value;

import java.util.Map;

public class ASTClosure { //let f = fun () -> 3 in f()
    public Exp code;
    public Map<String, Value> args; //can be 0 (no args)
    public Env<Value> environment;

    public ASTClosure (Map<String, Value> args, Env<Value> environment, Exp code){
        this.args = args;
        this.environment = environment;
        this.code = code;
    }
    // fun (id1 : t1) (id2 : t2) : t3 -> e
    // fun (x : bool) -> x+x
}
