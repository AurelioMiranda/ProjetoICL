package types;

public class IntType implements Type {

	public static final IntType singleton = new IntType();
	
	private IntType() {}

	public static IntType getIntType(){
		return singleton;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}

	@Override
	public String toString(){
		return "Int";
	}
}
