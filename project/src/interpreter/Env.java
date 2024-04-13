package interpreter;

import java.util.Hashtable;
import java.util.Map;

public class Env<T> {

    private Map<String,T> table;
    private Env<T> prev;

    public Env() {
        table = new Hashtable<>(20);
    }

    public Env(Env<T> prev){
        this.prev = prev;
        this.table = new Hashtable<>(20);
    }

    public void bind(String id, T val) {
        table.put(id, val);
    }

    public T find(String id) {
        T val = table.get(id);
        if (val == null){
            return prev.find(id);
        }
        return val;
    }

    public Env<T> beginScope() {
        return new Env<>(this);
    }

    public Env<T> endScope() {
        //TODO: finish this one
        return null;
    }

}
