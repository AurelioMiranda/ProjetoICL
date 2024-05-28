package target.identifiers;

import target.Instruction;

public class InvokeSpecial extends Instruction {
    public InvokeSpecial(String method) {
        this.op = "invokespecial";
        this.args = new String[] { method };
    }
}
