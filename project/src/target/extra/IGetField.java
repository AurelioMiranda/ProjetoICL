package target.extra;

import target.Instruction;

public class IGetField extends Instruction {
    private final String fieldName;

    public IGetField(String fieldName) {
        this.op = "IGetField";
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "IGetField(" + fieldName + ")";
    }
}