package values;

public class BoolValue implements Value {
	private boolean value;
	
	public BoolValue(boolean value) {
		this.value = value;
	}

	public boolean getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Boolean.toString(value);
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof BoolValue && value == ((BoolValue)obj).getValue();
	}
	
	
	
	
}
