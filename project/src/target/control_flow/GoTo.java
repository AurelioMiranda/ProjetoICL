package target.control_flow;

import target.Instruction;

public class GoTo extends Instruction {
    private String target;

    public GoTo(Label target) {
        this.target = target.getName();
    }

    @Override
    public String toString() {
        return "goto " + target;
    }
}
