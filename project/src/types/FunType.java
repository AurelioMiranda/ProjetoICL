package types;

public class FunType implements Type {
    public Type parameterType;
    public Type type;
    public static final FunType singleton = new FunType();

    private FunType() {}

    public FunType (Type argument, Type type){
        this.parameterType = argument;
        this.type = type;
    }

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
