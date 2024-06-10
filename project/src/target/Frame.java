package target;

import types.Type;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    int nFields;
    List<Type> types;
    int id;
    Frame prev;

    public Frame(int nFields) {
        this.nFields = nFields;
        this.types = new ArrayList<>(nFields);
    }

    public void addType(Type type) {
        if (types.size() < nFields) {
            types.add(type);
        } else {
            throw new IllegalStateException("");
        }
    }

    public Type getType(int index) {
        if (index >= 0 && index < types.size()) {
            return types.get(index);
        } else {
            throw new IndexOutOfBoundsException("");
        }
    }

    public void setPrev(Frame prev) {
        this.prev = prev;
    }

    public Frame getPrev() {
        return prev;
    }

    @Override
    public String toString() {
        return " ";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
