package Model.Expression;

import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.util.Map;

public class ValueExpression implements Expression{
    private final Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable) {
        if (value.getType().equals(new IntType())) {
            IntValue i = (IntValue)value;
            return new IntValue(i.getValue());
        }
        else if(value.getType().equals(new BoolType())){
            BoolValue b = (BoolValue)value;
            return new BoolValue(b.getValue());
        }
        else if(value.getType().equals(new StringType()))
        {
            StringValue s = (StringValue)value;
            return new StringValue(s.getValue());
        }
        return null;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
