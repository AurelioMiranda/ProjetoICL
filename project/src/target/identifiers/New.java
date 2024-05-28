package target.identifiers;

import target.Instruction;

public class New extends Instruction {
    public New(String className) {
        this.op = "new";
        this.args = new String[] { className };
    }
}