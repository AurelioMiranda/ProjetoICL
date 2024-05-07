package target.compare;

import target.Instruction;

public class IEq extends Instruction {

    public IEq() {
        op = "if_icompeq";
        args = null;
    }
}