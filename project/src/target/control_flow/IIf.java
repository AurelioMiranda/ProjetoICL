package target.control_flow;

import target.Instruction;

public class IIf  extends Instruction {

    public IIf() {
        op = "ifne";
        args = null;
    }

    public IIf(Label L) {
        op = "ifne " + L.getName();
        args = null;
    }
}
