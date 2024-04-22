package ast.arithmetic;

import ast.Exp;
import interpreter.Env;

public class ASTMult implements Exp {
	public Exp arg1;
	public Exp arg2;
	
	public ASTMult(Exp arg1, Exp arg2) {
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

	@Override
	public <T> T accept(Visitor<T, Env<T>> v) {
		return v.visit(this);
	}
}
