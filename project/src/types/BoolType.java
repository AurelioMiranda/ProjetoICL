package types;

public class BoolType implements Type {

    public static final BoolType singleton = new BoolType();

    private BoolType() {}

    public static BoolType getBoolType(){
        return singleton;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString(){
        return "Bool";
    }

}
