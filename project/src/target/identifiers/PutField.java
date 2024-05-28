package target.identifiers;

import target.Instruction;

public class PutField extends Instruction {
    public PutField(String field, String type) {
        this.op = "putfield";
        this.args = new String[] { field, type };
    }
}
