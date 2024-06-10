package target.compare;

import target.Instruction;
import target.control_flow.Label;

public class IEq extends Instruction {

    public IEq() {
        op = "if_icmpeq";
        args = null;
    }

    public IEq(Label l) {
        op = "if_icmpeq " + l.getName();
        args = null;
    }
}