package types;

public class FunType implements Type {

    public static final FunType singleton = new FunType();

    private FunType() {}

    public static FunType getFunType(){
        return singleton;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString(){
        return "Function";
    }

}
