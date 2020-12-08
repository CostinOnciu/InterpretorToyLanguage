package Model.Expression.LogicExpression;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.VariableNotBoolean;
import Model.Expression.Expression;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.Value;

import java.util.Map;

public class OrExpression extends LogicExpression{
    public OrExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable, Map<Integer,Value> heap) throws MyExceptions {
        if(left.evaluate(symbolTable,heap).getType().equals(new IntType()))
            throw new VariableNotBoolean("The left expression does not return an boolean");
        if(right.evaluate(symbolTable,heap).getType().equals(new IntType()))
            throw new VariableNotBoolean("The right expression does not return an boolean");

        var leftBool = (BoolValue)left.evaluate(symbolTable,heap);
        var rightBool = (BoolValue)right.evaluate(symbolTable,heap);

        return new BoolValue(leftBool.getValue()|rightBool.getValue());
    }
}
