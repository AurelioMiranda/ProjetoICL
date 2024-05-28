package target.identifiers;

import target.Instruction;

public class GetField extends Instruction {
    public GetField(String field, String type) {
        this.op = "getfield";
        this.args = new String[] { field, type };
    }
}
