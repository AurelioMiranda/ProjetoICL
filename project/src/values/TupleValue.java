package values;

public class TupleValue<V extends Value> implements Value {
    private final V first;
    private final V second;

    public TupleValue(V first, V second) {
        this.first = first;
        this.second = second;
    }

    public V getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "{" + first + ", " + second + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TupleValue<?> that = (TupleValue<?>) obj;
        return first.equals(that.first) && second.equals(that.second);
    }

    @Override
    public int hashCode() {
        int result = first.hashCode();
        result = 31 * result + second.hashCode();
        return result;
    }
}