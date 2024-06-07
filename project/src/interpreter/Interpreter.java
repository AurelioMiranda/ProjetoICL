package interpreter;

import ast.*;
import ast.arithmetic.ASTAdd;
import ast.arithmetic.ASTDiv;
import ast.arithmetic.ASTMult;
import ast.arithmetic.ASTSub;
import ast.control_flow.ASTElse;
import ast.control_flow.ASTIf;
import ast.control_flow.ASTWhile;
import ast.string.ASTConcat;
import ast.string.ASTString;
import ast.tuples.*;
import ast.identifiers.ASTIdentifier;
import ast.identifiers.ASTLet;
import ast.logical.*;
import ast.references.ASTAssign;
import ast.references.ASTDeref;
import ast.references.ASTNew;
import values.*;

import java.util.Map;


public class Interpreter implements ast.Exp.Visitor<Value, Env<Value>> {
    private static Memory memory = new Memory();

    private static Env<Value> env;

    @Override
    public Value visit(ASTInt i) {
        return new IntValue(i.value);
    }

    @Override
    public Value visit(ASTBool astbool) {
        return new BoolValue(astbool.value);
    }

    @Override
    public Value visit(ASTNeg astNeg) {
        Value v = astNeg.e.accept(this);
        if (v instanceof BoolValue v1) {
            return new BoolValue(!v1.getValue());
        }
        throw new ArithmeticException("~ can only be applied to booleans.");
    }

    @Override
    public Value visit(ASTMult e) {
        Value v = e.arg1.accept(this);
        if (v instanceof IntValue v1) {
            v = e.arg2.accept(this);
            if (v instanceof IntValue v2) {
                return new IntValue(v1.getValue() * v2.getValue());
            }
        }
        throw new ArithmeticException("Multiplication (*) can only be applied to numbers.");
    }

    @Override
    public Value visit(ASTSub e) {
        Value v = e.arg1.accept(this);
        if (v instanceof IntValue v1) {
            v = e.arg2.accept(this);
            if (v instanceof IntValue v2) {
                return new IntValue(v1.getValue() - v2.getValue());
            }
        }
        throw new ArithmeticException("Subtraction (-) can only be applied to numbers.");
    }

    @Override
    public Value visit(ASTEq e) {
        Value v = e.e1.accept(this);
        if (v instanceof IntValue v1) {
            v = e.e2.accept(this);
            if (v instanceof IntValue v2) {
                if (v1.getValue() == v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        if (v instanceof BoolValue v1) {
            v = e.e2.accept(this);
            if (v instanceof BoolValue v2) {
                if (v1.getValue() == v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        throw new ArithmeticException("Equals (=) can only be applied to numbers or booleans.");
    }

    @Override
    public Value visit(ASTAnd astAnd) {
        Value v = astAnd.e1.accept(this);
        if (v instanceof BoolValue v1) {
            if (!v1.getValue()) {
                return new BoolValue(false);
            }
            v = astAnd.e2.accept(this);
            if (v instanceof BoolValue v2) {
                if (v1.getValue() && v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        throw new ArithmeticException("And (&&) can only be applied to booleans.");
    }

    @Override
    public Value visit(ASTOr astOr) {
        Value v = astOr.e1.accept(this);
        if (v instanceof BoolValue v1) {
            if (v1.getValue()) {
                return new BoolValue(true);
            }
            v = astOr.e2.accept(this);
            if (v instanceof BoolValue v2) {
                if (v1.getValue() || v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        throw new ArithmeticException("Or (||) can only be applied to booleans.");
    }

    @Override
    public Value visit(ASTGr astGr) {
        Value v = astGr.e1.accept(this);
        if (v instanceof IntValue v1) {
            v = astGr.e2.accept(this);
            if (v instanceof IntValue v2) {
                if (v1.getValue() > v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        throw new ArithmeticException("Greater than (>) can only be applied to booleans.");
    }

    @Override
    public Value visit(ASTLt astLt) {
        Value v = astLt.e1.accept(this);
        if (v instanceof IntValue v1) {
            v = astLt.e2.accept(this);
            if (v instanceof IntValue v2) {
                if (v1.getValue() < v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        throw new ArithmeticException("Lesser than (<) can only be applied to booleans.");
    }

    @Override
    public Value visit(ASTGrOrEq astGrOrEq) {
        Value v = astGrOrEq.e1.accept(this);
        if (v instanceof IntValue v1) {
            v = astGrOrEq.e2.accept(this);
            if (v instanceof IntValue v2) {
                if (v1.getValue() >= v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        throw new ArithmeticException("Greater than or equal (>=) can only be applied to booleans.");
    }

    @Override
    public Value visit(ASTLTOrEq astltOrEq) {
        Value v = astltOrEq.e1.accept(this);
        if (v instanceof IntValue v1) {
            v = astltOrEq.e2.accept(this);
            if (v instanceof IntValue v2) {
                if (v1.getValue() <= v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        throw new ArithmeticException("Lesser than or equal (<=) can only be applied to booleans.");
    }

    @Override
    public Value visit(ASTNEq astneq) {
        Value v = astneq.e1.accept(this);
        if (v instanceof IntValue v1) {
            v = astneq.e2.accept(this);
            if (v instanceof IntValue v2) {
                if (v1.getValue() != v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        if (v instanceof BoolValue v1) {
            v = astneq.e2.accept(this);
            if (v instanceof BoolValue v2) {
                if (v1.getValue() != v2.getValue())
                    return new BoolValue(true);
                return new BoolValue(false);
            }
        }
        throw new ArithmeticException("Not equal (!=) can only be applied to numbers or booleans.");
    }


    @Override
    public Value visit(ASTAdd e) {
        Value v1 = e.arg1.accept(this);
        if (v1 instanceof IntValue) {
            Value v2 = e.arg2.accept(this);
            if (v2 instanceof IntValue) {
                return new IntValue(((IntValue) v1).getValue() + ((IntValue) v2).getValue());
            }
        }
        throw new ArithmeticException("Addition (+) can only be applied to numbers.");
    }


    @Override
    public Value visit(ASTDiv e) {
        Value v = e.arg1.accept(this);
        if (v instanceof IntValue v1) {
            v = e.arg2.accept(this);
            if (v instanceof IntValue v2) {
                if (v2.getValue() == 0) {
                    throw new ArithmeticException();
                }
                return new IntValue(v1.getValue() / v2.getValue());
            }
        }
        throw new ArithmeticException("Division (/) can only be applied to numbers.");
    }


    public Value visit(ASTIdentifier e) {
        Value v = (Value) env.find(e.getName());
        if (v instanceof IntValue v1) {
            return new IntValue(v1.getValue());
        } else if (v instanceof BoolValue v1) {
            return new BoolValue(v1.getValue());
        } else if (v instanceof ClosureValue c1) {
            return c1;
        } else if (v instanceof RefValue r1) {
            return r1;  // Return the reference value
        } else if (v instanceof TupleValue t1) {
            return t1;
        } else if (v instanceof StringValue t1) {
            return t1;
        }
        throw new ArithmeticException("Invalid identifier value.");
    }



    @Override
    public Value visit(ASTLet astLet) {
        env = env.beginScope();
        for (Map.Entry<String, Exp> entry : astLet.variables.entrySet()) {
            String variableName = entry.getKey();
            Exp variableValue = entry.getValue();
            Value v1 = interpret(variableValue, env);
            env.bind(variableName, v1);
        }
        Value val = interpret(astLet.body, env);
        env = env.endScope();
        return val;
    }

    @Override
    public Value visit(ASTIf astIf) {
        Value cond = interpret(astIf.condition, env);
        if (cond instanceof BoolValue v1) {
            if (v1.getValue()) {
                return interpret(astIf.body, env);
            }
            return new UnitValue();
        }
        throw new ArithmeticException();
    }

    @Override
    public Value visit(ASTElse astElse) {
        Value cond = interpret(astElse.condition, env);
        if (cond instanceof BoolValue v1) {
            if (v1.getValue()) {
                return interpret(astElse.ifBody, env);
            } else if (!v1.getValue()) {
                return interpret(astElse.elseBody, env);
            }
            return new UnitValue();
        }
        throw new ArithmeticException();
    }

    @Override
    public Value visit(ASTWhile astWhile) { //TODO: while needs memory to work
        Value cond = interpret(astWhile.condition, env);
        if (cond instanceof BoolValue v1) {
            if (v1.getValue()) {
                return interpret(new ASTWhile(astWhile.condition, astWhile.body), env);
            }
            return new UnitValue();
        }
        throw new ArithmeticException();
    }

    @Override
    public Value visit(ASTClosure astClosure) {
        Env<Value> closureEnv = env.beginScope();
        for (ASTParameter param : astClosure.params) {
            closureEnv.bind(param.identifier, new UnitValue());
        }
        return new ClosureValue(astClosure.params.get(0).identifier, astClosure.code, closureEnv);
    }


    @Override
    public Value visit(ASTCall astCall) {
        Value callee = interpret(astCall.identifier, env);

        if (callee instanceof ClosureValue closureValue) {
            Env<Value> extendedEnv = closureValue.env.beginScope();
            Value argValue = interpret(astCall.arguments, env);
            extendedEnv.bind(closureValue.param, argValue);
            return interpret(closureValue.body, extendedEnv);
        }
        throw new RuntimeException("Not a function value");
    }

    @Override
    public Value visit(ASTAssign astAssign) {
        Value lhsValue = interpret(astAssign.getLhs(), env);
        Value rhsValue = interpret(astAssign.getRhs(), env);
        if (lhsValue instanceof RefValue) {
            int address = ((RefValue) lhsValue).getAddress();
            memory.set(address, rhsValue);
            return rhsValue;
        } else {
            throw new RuntimeException("Left-hand side of assignment must be a reference type");
        }
    }

    @Override
    public Value visit(ASTNew astNew) {
        Value value = interpret(astNew.expression, env);
        int address = memory.allocate(value);
        return new RefValue(address);
    }


    @Override
    public Value visit(ASTDeref astDeref) {
        Value refValue = interpret(astDeref.expression, env);
        if (refValue instanceof RefValue) {
            int address = ((RefValue) refValue).getAddress();
            Value value = memory.get(address);
            return value;
        } else {
            throw new RuntimeException("Dereference operation requires a reference value");
        }
    }

    @Override
    public Value visit(ASTPair astPair) {
        TupleValue tv = new TupleValue();

        for (Exp tuple: astPair.tupleList) {
            Value v1 = tuple.accept(this);
            tv.addValue(v1);
        }

        return tv;
    }

    @Override
    public Value visit(ASTFirst astFirst) {
        Value t1 = interpret(astFirst.tuple, env);

        if (t1 instanceof TupleValue) {
            return ((TupleValue) t1).getFirst();
        }
        throw new RuntimeException("first can only be applied to Tuple.");
    }

    @Override
    public Value visit(ASTSecond astSecond) {
        Value t1 = interpret(astSecond.tuple, env);

        if (t1 instanceof TupleValue) {
            return ((TupleValue) t1).getSecond();
        }
        throw new RuntimeException("second can only be applied to Tuple.");
    }

    @Override
    public Value visit(ASTLast astLast) {
        Value t1 = interpret(astLast.tuple, env);

        if (t1 instanceof TupleValue) {
            return ((TupleValue) t1).getLast();
        }
        throw new RuntimeException("last can only be applied to Tuple.");
    }

    @Override
    public Value visit(ASTMatch astMatch) { //TODO: continue
        return null;
    }

    @Override
    public Value visit(ASTString astString) {
        return new StringValue(astString.value);
    }

    @Override
    public Value visit(ASTConcat astConcat) {
        Value v1 = interpret(astConcat.arg1, env);
        Value v2 = interpret(astConcat.arg2, env);

        if (v1 instanceof StringValue && v2 instanceof StringValue) {
            return new StringValue(((StringValue) v1).getValue()+((StringValue) v2).getValue());
        }

        throw new RuntimeException("concat can only be applied to Strings.");
    }


    public static Value interpret(Exp e, Env<Value> env) {
        Interpreter i = new Interpreter();
        Interpreter.env = env;
        return e.accept(i);
    }
}
