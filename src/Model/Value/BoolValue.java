package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value{
    boolean value;

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
    public Type getType() {
        return new BoolType();
    }
}
