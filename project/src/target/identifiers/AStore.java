package target.identifiers;

import target.Instruction;

public class AStore extends Instruction {
    public AStore(int index) {
        this.op = "astore";
        this.args = new String[] { Integer.toString(index) };
    }
}