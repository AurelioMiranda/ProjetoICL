package types;

public class StringType implements Type {

    public static final StringType singleton = new StringType();

    private StringType() {}

    public static StringType getStringType(){
        return singleton;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString(){
        return "String";
    }
}