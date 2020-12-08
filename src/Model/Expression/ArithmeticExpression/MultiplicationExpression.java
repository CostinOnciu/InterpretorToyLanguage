package Model.Expression.ArithmeticExpression;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.VariableNotInteger;
import Model.Expression.Expression;
import Model.Type.BoolType;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Map;

public class MultiplicationExpression extends ArithmeticExpression{
    public MultiplicationExpression(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public String toString() {
        return "("+left.toString() + "*" + right.toString()+")";
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable, Map<Integer,Value> heap) throws MyExceptions {
        if(left.evaluate(symbolTable,heap).getType().equals(new BoolType()))
            throw new VariableNotInteger("The left expression does not return an integer");
        if(right.evaluate(symbolTable,heap).getType().equals(new BoolType()))
            throw new VariableNotInteger("The right expression does not return an integer");

        var leftInt = (IntValue)left.evaluate(symbolTable,heap);
        var rightInt = (IntValue)right.evaluate(symbolTable,heap);

        return new IntValue(leftInt.getValue()*rightInt.getValue());
    }
}
