package ast;

import interpreter.Env;
import types.*;

public class ASTParameter {
    public String identifier;
    public Type type;

    public ASTParameter(String identifier, String rawType) {
        this.identifier = identifier;
        this.type = switch (rawType) { //  unit | int | bool | ref
            case "unit" -> UnitType.getUnitType();
            case "bool" -> BoolType.getBoolType();
            case "int" -> IntType.getIntType();
            case "ref" -> RefType.getReferencedType(); //TODO: This might be an issue
            default -> UnitType.getUnitType();
        };
    }

    public String getIdentifier() {
        return identifier;
    }

    public Type getType() {
        return type;
    }
}
