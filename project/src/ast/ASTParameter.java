package ast;

import interpreter.Env;
import types.*;

public class ASTParameter implements ast.Exp {
    public String identifier;
    public Type type;

    public ASTParameter(String identifier, String rawType) {
        this.identifier = identifier;
        this.type = switch (rawType) { //  unit | int | bool | ref
            case "unit" -> UnitType.getUnitType();
            case "bool" -> BoolType.getBoolType();
            case "int" -> IntType.getIntType();
            case "ref" -> IntType.getIntType(); //TODO: Ref type instance
            default -> UnitType.getUnitType();
        };
    }

    public String getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return type;
    }

    @Override
    public <T> T accept(Visitor<T, Env<T>> v) {
        return v.visit(this);
    }
}
