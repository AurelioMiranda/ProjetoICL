package target.memory;

import target.Instruction;

public class IAlloc extends Instruction {
    public IAlloc() {
        op = "alloc";
        args = null; // No arguments needed for allocation
    }

    @Override
    public String toString() {
        return "Alloc{}";
    }
}

