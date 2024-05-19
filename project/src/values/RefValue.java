package values;

public class RefValue implements Value {
    private int address;

    public RefValue(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RefValue refValue = (RefValue) obj;
        return address == refValue.address;
    }

    @Override
    public String toString() {
        return "Ref(" + address + ")";
    }
}
