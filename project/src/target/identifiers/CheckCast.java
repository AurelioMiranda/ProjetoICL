package target.identifiers;

import target.Instruction;

public class CheckCast extends Instruction {
    public CheckCast(String className) {
        this.op = "checkcast";
        this.args = new String[] { className };
    }
}