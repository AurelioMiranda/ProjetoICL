package target.compare;

import target.Instruction;

public class ILtOrEq extends Instruction {

    public ILtOrEq() {
        op = "if_icomple";
        args = null;
    }
}