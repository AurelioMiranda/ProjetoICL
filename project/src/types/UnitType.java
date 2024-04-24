package types;

public class UnitType implements Type {

    public static final UnitType singleton = new UnitType();

    private UnitType() {}

    public static UnitType getUnitType(){
        return singleton;
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    @Override
    public String toString(){
        return "Unit";
    }

}
