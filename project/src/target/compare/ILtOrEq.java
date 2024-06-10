package target.compare;

import target.Instruction;
import target.control_flow.Label;

public class ILtOrEq extends Instruction {

    public ILtOrEq() {
        op = "if_icmple";
        args = null;
    }

    public ILtOrEq(Label l1) {
        op = "if_icmple " + l1.getName();
        args = null;
    }
}