package values;

import ast.Exp;
import interpreter.Env;

public class ClosureValue  implements Value {

    public final String param;
    public final Exp body;
    public final Env<Value> env;

    public ClosureValue(String param, Exp body, Env<Value> env) {
        this.param = param;
        this.body = body;
        this.env = env;
    }
}