package target.compare;

import target.Instruction;

public class IGr extends Instruction {

    public IGr() {
        op = "if_icmple";
        args = null;
    }

    public IGr(String s) {
        op = "if_icmple " + s;
        args = null;
    }
}