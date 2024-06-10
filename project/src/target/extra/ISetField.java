package target.extra;

import target.Instruction;

public class ISetField extends Instruction {
    private final String fieldName;

    public ISetField(String fieldName) {
        this.op = "ISetField";
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "ISetField(" + fieldName + ")";
    }
}