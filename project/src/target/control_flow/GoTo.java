package target.control_flow;

import target.Instruction;

public class GoTo extends Instruction {
    private Label target;

    public GoTo(Label target) {
        this.target = target;
    }

    public Label getTarget() {
        return target;
    }

    public void setTarget(Label target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "GoTo{" +
                "target=" + target +
                '}';
    }
}
