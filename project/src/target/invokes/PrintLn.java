package target.invokes;

import target.Instruction;

public class PrintLn extends Instruction {

    public PrintLn(String type) {
        op = "invokevirtual java/io/PrintStream/println(" + type + ")V";
        args = null;
    }
}
