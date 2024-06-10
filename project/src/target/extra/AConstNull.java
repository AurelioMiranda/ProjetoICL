package target.extra;

import target.Instruction;

public class AConstNull extends Instruction {
    public AConstNull() {
        op = "aconst_null";
        args = null;
    }

    @Override
    public String toString() {
        return op;
    }
}