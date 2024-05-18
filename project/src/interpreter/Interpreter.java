package interpreter;

import ast.*;
import ast.arithmetic.ASTAdd;
import ast.arithmetic.ASTDiv;
import ast.arithmetic.ASTMult;
import ast.arithmetic.ASTSub;
import ast.control_flow.ASTElse;
import ast.control_flow.ASTIf;
import ast.control_flow.ASTWhile;
import ast.identifiers.ASTIdentifier;
import ast.identifiers.ASTLet;
import ast.logical.*;
import types.Type;
import values.*;


public class Interpreter implements ast.Exp.Visitor<Value, Env<Value>> {
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
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
        throw new ArithmeticException();
    }

    @Override
    public Value visit(ASTAdd e) {
        Value v = e.arg1.accept(this);
        if (v instanceof IntValue v1) {
            v = e.arg2.accept(this);
            if (v instanceof IntValue v2) {
                return new IntValue(v1.getValue() + v2.getValue());
            }
        }
        throw new ArithmeticException();
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
        throw new ArithmeticException();
    }


    public Value visit(ASTIdentifier e) {
        Value v = (Value) env.find(e.getName());
        if (v instanceof IntValue v1) {
            return new IntValue(((IntValue) v).getValue());
        } else if (v instanceof BoolValue v1) {
            return new BoolValue(((BoolValue) v).getValue());
        } else if (v instanceof  ClosureValue c1) {
            return c1;
        }
        throw new ArithmeticException();
    }

    @Override
    public Value visit(ASTNew astNew) {
        return null;
    }

    @Override
    public Value visit(ASTLet astLet) {
        Value v1 = interpret(astLet.variableValue, env);
        env = env.beginScope();
        env.bind(astLet.variableName, v1);
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
    public Value visit(ASTParameter astParameter) {
        return null;
    }

    @Override
    public Value visit(ASTCall astCall) { //TODO: how does this work... i genuinely don't understand
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
        return null;
    }


    public static Value interpret(Exp e, Env<Value> env) {
        Interpreter i = new Interpreter();
        Interpreter.env = env;
        return e.accept(i);
    }
}
