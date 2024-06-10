package target.control_flow;

import target.Instruction;

public class Label extends Instruction {
    private String name;

    public Label(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString() {
        return name + ":";
    }
}
