package Model.Value;

import Model.Type.ReferenceType;
import Model.Type.Type;

import java.util.Objects;

public class ReferenceValue implements Value{
    private int address;
    private Type valueType;

    public int getAddress() {
        return address;
    }

    public Type getValueType() {
        return valueType;
    }

    public ReferenceValue(int address, Type valueType) {
        this.address = address;
        this.valueType = valueType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceValue that = (ReferenceValue) o;
        return Objects.equals(valueType, that.valueType) && address == that.getAddress();
    }

    @Override
    public String toString() {
        return "("+ address + ", " + valueType.toString()+")";
    }

    @Override
    public Type getType() {
        return new ReferenceType(valueType);
    }
}
