package interpreter;

import values.Value;

import java.util.HashMap;
import java.util.Map;

public class Memory {
    private Map<Integer, Value> memory;
    private int nextAddress;

    public Memory() {
        memory = new HashMap<>();
        nextAddress = 0;
    }

    public int allocate(Value value) {
        int address = nextAddress++;
        memory.put(address, value);
        return address;
    }

    public Value get(int address) {
        return memory.get(address);
    }

    public void set(int address, Value value) {
        memory.put(address, value);
    }
}
