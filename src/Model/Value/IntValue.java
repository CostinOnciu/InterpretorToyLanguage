package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

import java.util.Objects;

public class IntValue implements Value {
    private final int value;

    public IntValue(int value) {
        this.value = value;
    }
    public IntValue(IntValue value) {
        this.value = value.value;
    }

    public int getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntValue intValue = (IntValue) o;
        return value == intValue.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString(){
        return String.valueOf(value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }
}