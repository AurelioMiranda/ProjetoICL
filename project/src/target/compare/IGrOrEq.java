package target.compare;

import target.Instruction;
import target.control_flow.Label;

public class IGrOrEq extends Instruction {

    public IGrOrEq() {
        op = "if_icmpge";
        args = null;
    }

    public IGrOrEq(Label l1) {
        op = "if_icmpge " + l1.getName();
        args = null;
    }
}