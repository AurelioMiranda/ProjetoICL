package target.identifiers;

import target.Instruction;

public class Dup extends Instruction {
    public Dup() {
        this.op = "dup";
        this.args = null;
    }
}
