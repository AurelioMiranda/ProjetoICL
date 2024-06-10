package target.compare;

import target.Instruction;
import target.control_flow.Label;

public class IXor extends Instruction {

    public IXor() {
        op = "ixor";
        args = null;
    }

    public IXor(Label l1) {
        op = "ixor " + l1.getName();
        args = null;
    }
}