package Model.Expression.LogicExpression;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.VariableNotBoolean;
import Model.Expression.Expression;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.Value;

import java.util.Map;

public class OrExpression extends LogicExpression{
    private final Expression left;
    private final Expression right;

    public OrExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable) throws MyExceptions {
        if(left.evaluate(symbolTable).getType().equals(new IntType()))
            throw new VariableNotBoolean("The left expression does not return an boolean");
        if(right.evaluate(symbolTable).getType().equals(new IntType()))
            throw new VariableNotBoolean("The right expression does not return an boolean");

        var leftBool = (BoolValue)left.evaluate(symbolTable);
        var rightBool = (BoolValue)right.evaluate(symbolTable);

        return new BoolValue(leftBool.getValue()|rightBool.getValue());
    }
}
