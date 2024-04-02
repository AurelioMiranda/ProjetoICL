package target;

import java.util.List;

public abstract class Instruction {
	public String op;
	public String[] args;

	public int numArgs() {
		if ( args == null ) 
			return 0;
		else
			return args.length;
	}
	
	public String toString() {
		String argList = "";
		if (args != null) {
			argList = " ";
			for (int i=0;i<args.length-1;i++)
				argList += args[i] + " ";
			argList += args[args.length-1];
		}
		return op + argList;
	}
	
	
}
