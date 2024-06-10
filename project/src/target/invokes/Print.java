package target.invokes;

import target.Instruction;

public class Print extends Instruction {

    public Print() {
        op = "invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V";
        args = null;
    }
}
