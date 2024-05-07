package target.compare;

import target.Instruction;

public class IGrOrEq extends Instruction {

    public IGrOrEq() {
        op = "if_icompge";
        args = null;
    }
}