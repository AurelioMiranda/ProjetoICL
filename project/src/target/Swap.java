package target;

public class Swap extends Instruction {
    public Swap() {
        this.op = "swap";
    }

    @Override
    public String toString() {
        return op;
    }
}