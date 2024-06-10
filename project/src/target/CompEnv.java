package target;

import interpreter.Env;
import utils.Pair;

import java.util.HashMap;
import java.util.Map;

public class CompEnv<T> {

    private Map<String, Integer> table;
    private CompEnv<T> prev;

    public CompEnv(){
        this.table = new HashMap<>();
    }

    public CompEnv(CompEnv<T> prev){
        this.table = new HashMap<>();
        this.prev = prev;
    }

    public CompEnv<T> getPrev(){
        return prev;
    }

    private Pair<Integer,Integer> findIt(String id) {
        Integer width;
        CompEnv env = this;
        int depth = -1;
        do {
            width = (Integer)env.table.get(id);
            env = this.prev;
            depth++;
        } while (width == null && env != null);

        return new Pair<>(depth,width);
    }

    public Pair<Integer,Integer> find(String id) {
        return findIt(id);
    }
}
