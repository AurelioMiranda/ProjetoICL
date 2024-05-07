package target.compare;

import target.Instruction;

public class ILt extends Instruction {

    public ILt() {
        op = "if_icomplt";
        args = null;
    }
}