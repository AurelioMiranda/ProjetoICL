package values;

public class RefValue implements Value{
    private Value value;

    public RefValue(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    //fala sobre o valor da casa a referencia em si
    public void setValue(Value value) {
        this.value = value;
    }
    @Override
    public boolean equals(Object obj) {
        return obj instanceof RefValue && value == ((RefValue)obj).getValue();
    }
    @Override
    public String toString() {
        return "ref("+value.toString()+")";
    }

}
