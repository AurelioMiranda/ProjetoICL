package interpreter;

import ast.*;
import values.BoolValue;
import values.IntValue;
import values.Value;


public class Interpreter implements ast.Exp.Visitor<Value> {

	@Override
	public Value visit(ASTInt i) {
		return new IntValue(i.value);
	}

	@Override
	public Value visit(ASTMult e) {
		Value v = e.arg1.accept(this);
		if (v instanceof IntValue v1){
			v = e.arg2.accept(this);
			if (v instanceof IntValue v2){
				return new IntValue(v1.getValue() * v2.getValue());
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTSub e) {
		Value v = e.arg1.accept(this);
		if (v instanceof IntValue v1){
			v = e.arg2.accept(this);
			if (v instanceof IntValue v2){
				return new IntValue(v1.getValue() - v2.getValue());
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTEq e) {
		Value v = e.e1.accept(this);
		if (v instanceof IntValue v1){
			v = e.e2.accept(this);
			if (v instanceof IntValue v2){
				if (v1.getValue() == v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		if (v instanceof BoolValue v1){
			v = e.e2.accept(this);
			if (v instanceof BoolValue v2){
				if (v1.getValue() == v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTAnd astAnd) {
		Value v = astAnd.e1.accept(this);
		if (v instanceof IntValue v1){
			v = astAnd.e2.accept(this);
			if (v instanceof IntValue v2){
				if (v1.getValue() >= 0 && v2.getValue() >= 0)
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		if (v instanceof BoolValue v1){
			v = astAnd.e2.accept(this);
			if (v instanceof BoolValue v2){
				if (v1.getValue() && v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTOr astOr) {
		Value v = astOr.e1.accept(this);
		if (v instanceof IntValue v1){
			v = astOr.e2.accept(this);
			if (v instanceof IntValue v2){
				if (v1.getValue() >= 0 || v2.getValue() >= 0)
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		if (v instanceof BoolValue v1){
			v = astOr.e2.accept(this);
			if (v instanceof BoolValue v2){
				if (v1.getValue() || v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTGr astGr) {
		Value v = astGr.e1.accept(this);
		if (v instanceof IntValue v1){
			v = astGr.e2.accept(this);
			if (v instanceof IntValue v2){
				if (v1.getValue() > v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTLt astLt) {
		Value v = astLt.e1.accept(this);
		if (v instanceof IntValue v1){
			v = astLt.e2.accept(this);
			if (v instanceof IntValue v2){
				if (v1.getValue() < v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTGrOrEq astGrOrEq) {
		Value v = astGrOrEq.e1.accept(this);
		if (v instanceof IntValue v1){
			v = astGrOrEq.e2.accept(this);
			if (v instanceof IntValue v2){
				if (v1.getValue() >= v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTLTOrEq astltOrEq) {
		Value v = astltOrEq.e1.accept(this);
		if (v instanceof IntValue v1){
			v = astltOrEq.e2.accept(this);
			if (v instanceof IntValue v2){
				if (v1.getValue() <= v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTNEq astneq) {
		Value v = astneq.e1.accept(this);
		if (v instanceof IntValue v1){
			v = astneq.e2.accept(this);
			if (v instanceof IntValue v2){
				if (v1.getValue() != v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		if (v instanceof BoolValue v1){
			v = astneq.e2.accept(this);
			if (v instanceof BoolValue v2){
				if (v1.getValue() != v2.getValue())
					return new BoolValue(true);
				return new BoolValue(false);
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTAdd e) {
		Value v = e.arg1.accept(this);
		if (v instanceof IntValue v1){
			v = e.arg2.accept(this);
			if (v instanceof IntValue v2){
				return new IntValue(v1.getValue() + v2.getValue());
			}
		}
		throw new ArithmeticException();
	}

	@Override
	public Value visit(ASTDiv e) {
		Value v = e.arg1.accept(this);
		if (v instanceof IntValue v1){
			v = e.arg2.accept(this);
			if (v instanceof IntValue v2){
				if(v2.getValue() == 0){
					throw new ArithmeticException();
				}
				return new IntValue(v1.getValue() / v2.getValue());
			}
		}
		throw new ArithmeticException();
	}


	public static Value interpret(Exp e) {
		Interpreter i = new Interpreter();
		return e.accept(i);

	}
}
