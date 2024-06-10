package types;

public class RefType implements Type {
    private final Type referencedType;

    public RefType(Type referencedType) {
        this.referencedType = referencedType;
    }

    public Type getReferencedType() {
        return referencedType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof RefType)) return false;
        RefType refType = (RefType) obj;
        return referencedType.equals(refType.referencedType);
    }

    @Override
    public String toString() {
        return "Ref(" + referencedType + ")";
    }
}