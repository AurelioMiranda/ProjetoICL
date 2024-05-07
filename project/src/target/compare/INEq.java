package target.compare;

import target.Instruction;

public class INEq extends Instruction {

    public INEq() {
        op = "if_icompne";
        args = null;
    }
}