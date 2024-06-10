package target.invokes;

import target.Instruction;

public class typeToString extends Instruction {

    public typeToString(String type) {
        op = "invokestatic java/lang/String/valueOf(" + type + ")Ljava/lang/String;";
        args = null;
    }
}
