package types;

public class TupleType implements Type {

    public static final TupleType singleton = new TupleType();

    private TupleType() {}

    public static TupleType getInstance(){
        return singleton;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString(){
        return "Tuple";
    }
}
