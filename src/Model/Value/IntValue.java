package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value {
    int value;

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() { return value; }

    @Override
    public String toString(){
        return String.valueOf(value);
    }

    @Override
    public Type getType() {
        return new IntType();
    }
}