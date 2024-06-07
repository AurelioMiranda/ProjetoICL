package values;

import java.util.Objects;

public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringValue && Objects.equals(value, ((StringValue) obj).getValue());
    }


}