package Model.Type;

import Model.Value.IntValue;
import Model.Value.Value;

public class IntType implements Type{
    public IntType(IntType type) {
    }

    public IntType() {
    }

    public boolean equals(Object another){
        return another instanceof IntType;
    }
    public String toString() { return "int";}

    @Override
    public Value defaultValue() {
        return new IntValue(0);
    }
}
