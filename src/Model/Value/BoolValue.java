package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

import java.util.Objects;

public class BoolValue implements Value{
    private final boolean value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoolValue boolValue = (BoolValue) o;
        return value == boolValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public BoolValue(boolean value) {
        this.value = value;
    }
    public BoolValue(BoolValue value){
        this.value = value.value;
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
