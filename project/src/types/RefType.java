package types;

public class RefType implements Type {
    private static Type referencedType;

    public RefType(Type referencedType) {
        this.referencedType = referencedType;
    }

    public static Type getReferencedType() {
        return referencedType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RefType refType = (RefType) obj;
        return referencedType.equals(refType.referencedType);
    }

    @Override
    public String toString() {
        return "Ref(" + referencedType.toString() + ")";
    }
}
