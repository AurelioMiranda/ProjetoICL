package target.compare;

import target.Instruction;

public class IGr extends Instruction {

    public IGr() {
        op = "if_icompgt";
        args = null;
    }
}