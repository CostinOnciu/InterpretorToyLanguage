package Model.Type;
import Model.Value.ReferenceValue;
import Model.Value.Value;

public class ReferenceType implements Type{
    public Type getInnerType() {
        return innerType;
    }

    private Type innerType;

    public ReferenceType(Type innerType) {
        this.innerType = innerType;
    }

    @Override
    public Value defaultValue() {
        return new ReferenceValue(0,innerType);
    }

    @Override
    public String toString() {
        return "ref(" + innerType.toString()+")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ReferenceType)
            return innerType.equals(((ReferenceType) obj).getInnerType());
        else
            return false;
    }
}
