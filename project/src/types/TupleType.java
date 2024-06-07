package types;

import java.util.List;

public class TupleType implements Type {

    private List<Type> elementTypes;

    public TupleType(List<Type> elementTypes) {
        this.elementTypes = elementTypes;
    }

    public List<Type> getElementTypes() {
        return elementTypes;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TupleType that = (TupleType) obj;
        return elementTypes.equals(that.elementTypes);
    }

    @Override
    public String toString(){
        return "Tuple";
    }
}
