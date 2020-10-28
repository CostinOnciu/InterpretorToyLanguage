package Model.Expression.ArithmeticExpression;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.VariableNotInteger;
import Model.Expression.Expression;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Map;

public class MultiplicationExpression extends ArithmeticExpression{
    Expression left;
    Expression right;

    public MultiplicationExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "("+left.toString() + "*" + right.toString()+")";
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable) throws MyExceptions {
        if(left.evaluate(symbolTable).getType().toString().equals("bool"))
            throw new VariableNotInteger("The left expression does not return an integer");
        if(right.evaluate(symbolTable).getType().toString().equals("bool"))
            throw new VariableNotInteger("The right expression does not return an integer");

        var leftInt = (IntValue)left.evaluate(symbolTable);
        var rightInt = (IntValue)right.evaluate(symbolTable);

        return new IntValue(leftInt.getValue()*rightInt.getValue());
    }
}
