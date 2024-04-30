package ast;

import interpreter.Env;
import values.Value;

public class ASTClosure { //let f = fun () -> 3 in f()
    public Exp code;
    public Exp args; //can be 0 (no args)
    public Exp environment;

}
