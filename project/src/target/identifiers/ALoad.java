package target.identifiers;

import target.Instruction;

public class ALoad extends Instruction {
    public ALoad(int index) {
        this.op = "aload";
        this.args = new String[] { Integer.toString(index) };
    }
}
