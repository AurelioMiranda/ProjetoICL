package types;

public class IntType implements IType {

	public static final IntType singleton = new IntType();
	
	private IntType() {}
	
//	@Override
//	public boolean equals(Object obj) {
//		return this == obj;
//	}

}
