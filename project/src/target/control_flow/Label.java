package target.control_flow;

import target.Instruction;

public class Label extends Instruction {
    private int position;

    public Label() { }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Label{" +
                "position=" + position +
                '}';
    }
}
