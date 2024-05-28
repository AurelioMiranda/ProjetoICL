package target.memory;

import target.Instruction;

public class ILoad extends Instruction {
    public ILoad() {
        op = "load";
        args = null; // The actual memory location is determined during execution
    }

    @Override
    public String toString() {
        return "Load{}";
    }
}
