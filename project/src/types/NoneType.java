package types;

public class NoneType implements Type {

    public static final NoneType singleton = new NoneType();

    private NoneType() {}

    public static NoneType getNoneType(){
        return singleton;
    }

    @Override
    public String toString(){
        return "None";
    }

}