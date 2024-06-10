package target.compare;

import target.Instruction;
import target.control_flow.Label;

public class ILt extends Instruction {

    public ILt() {
        op = "if_icmplt";
        args = null;
    }

    public ILt(Label l1) {
        op = "if_icmplt " + l1.getName();
        args = null;
    }
}