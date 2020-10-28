package Model.Expression;

import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Map;

public class ValueExpression implements Expression{
    Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable) {
        if (value.getType().toString().equals("int")) {
            IntValue i = (IntValue)value;
            return new IntValue(i.getValue());
        }
        else if(value.getType().toString().equals("bool")){
            BoolValue b = (BoolValue)value;
            return new BoolValue(b.getValue());
        }
        return null;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
