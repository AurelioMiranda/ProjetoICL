package target.memory;

import target.Instruction;

public class IStore extends Instruction {
    public IStore() {
        op = "store";
        args = null; // The actual memory location and value are determined during execution
    }

    @Override
    public String toString() {
        return "Store{}";
    }
}
