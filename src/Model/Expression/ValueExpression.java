package Model.Expression;

import Model.Exceptions.MyExceptions;
import Model.Type.*;
import Model.Value.*;

import java.util.Map;

public class ValueExpression implements Expression{
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable, Map<Integer,Value> heap) {
        if (value.getType().equals(new IntType())) {
            var i = (IntValue)value;
            return new IntValue(i.getValue());
        }
        else if(value.getType().equals(new BoolType())) {
            var b = (BoolValue)value;
            return new BoolValue(b.getValue());
        }
        else if(value.getType().equals(new StringType())) {
            var s = (StringValue)value;
            return new StringValue(s.getValue());
        }
        else if(value.getType() instanceof ReferenceType) {
            var r = (ReferenceValue)value;
            return new ReferenceValue(r.getAddress(),r.getValueType());
        }
        return null;
    }

    @Override
    public Type typeCheck(Map<String, Type> typeEnv) {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
