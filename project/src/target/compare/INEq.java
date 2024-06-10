package target.compare;

import target.Instruction;
import target.control_flow.Label;

public class INEq extends Instruction {

    public INEq() {
        op = "if_icmpne";
        args = null;
    }

    public INEq(Label l) {
        op = "if_icmpne " + l.getName();
        args = null;
    }
}