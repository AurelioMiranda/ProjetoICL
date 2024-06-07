package values;

import java.util.ArrayList;
import java.util.List;

public class TupleValue implements Value {
    private List<Value> tuples;

    public TupleValue() {
        this.tuples = new ArrayList<>();
    }

    public Value getFirst() {
        return tuples.get(0);
    }

    public Value getSecond() {
        return tuples.get(1);
    }

    public Value getLast() {
        return tuples.get(tuples.size()-1);
    }

    @Override
    public String toString() {
        return "{" + getFirst() + ", " + getSecond() + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TupleValue that = (TupleValue) obj;
        return getFirst().equals(that.getFirst()) && getSecond().equals(that.getSecond());
    }

    public void addValue(Value v1) {
        tuples.add(v1);
    }

    public List<Value> getValues() {
        return tuples;
    }
}