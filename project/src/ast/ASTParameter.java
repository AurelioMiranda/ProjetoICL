package ast;

import interpreter.Env;
import types.*;

public class ASTParameter {
    public String identifier;
    public Type type;

    public ASTParameter(String identifier, String rawType) {
        this.identifier = identifier;
        this.type = parseType(rawType);
    }

    private Type parseType(String rawType) {
        return switch (rawType) {
            case "unit" -> UnitType.getUnitType();
            case "bool" -> BoolType.getBoolType();
            case "int" -> IntType.getIntType();
            case "ref" -> {
                String referencedType = rawType.substring(4, rawType.length() - 1); // Extract the type inside the ref(...)
                yield new RefType(parseType(referencedType));
            }
            case "string" -> StringType.getStringType();
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
