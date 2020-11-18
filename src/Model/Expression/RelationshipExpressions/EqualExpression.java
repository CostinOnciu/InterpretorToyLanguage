package Model.Expression.RelationshipExpressions;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.VariableNotInteger;
import Model.Expression.Expression;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Map;

public class EqualExpression extends RelationshipExpression{
    private final Expression left;
    private final Expression right;

    public EqualExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable, Map<Integer,Value> heap) throws MyExceptions {
        if(left.evaluate(symbolTable,heap).getType().equals(new IntType())) {
            if (right.evaluate(symbolTable,heap).getType().equals(new IntType())) {
                var l = (IntValue) left.evaluate(symbolTable,heap);
                var r = (IntValue) right.evaluate(symbolTable,heap);
                return new BoolValue(l.getValue() == r.getValue());
            }
            else
                throw new VariableNotInteger(left.toString()+" -> Invalid right expression");
        }
        else
            throw new VariableNotInteger(right.toString()+" -> Invalid right expression");
    }
}
