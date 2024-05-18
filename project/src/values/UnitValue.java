package values;

public class UnitValue implements Value {

    public UnitValue() {
    }

    public String getValue() {
        return "()";
    }

    @Override
    public String toString() {
        return "()";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof UnitValue && "())".equals(((UnitValue) obj).getValue());
    }




}
