package types;

public class RefType {
    private Type innerType;

    public RefType(Type innerType) {
        this.innerType = innerType;
    }

    public Type getInnerType() {
        return innerType;
    }

    public boolean equals(Object obj) {
        return this == obj;
    }


    @Override
    public String toString() {
        return "RefType(" + innerType.toString() + ")";
    }
}
