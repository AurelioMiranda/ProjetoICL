package target.extra;

import target.Instruction;

public class INewTuple extends Instruction {

    public INewTuple() {
        this.op = "INewTuple";
    }

    @Override
    public String toString() {
        return "INewTuple()";
    }
}