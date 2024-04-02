package target;

public class SIPush extends Instruction {
	public SIPush(int n) {
		op="sipush";
		args = new String[]{ Integer.toString(n) } ; 
	}
}
