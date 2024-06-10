package target.getters;

import target.Instruction;

public class SoutPrintStream extends Instruction {

    public SoutPrintStream() {
        op = "getstatic java/lang/System/out Ljava/io/PrintStream;";
        args = null;
    }
}
