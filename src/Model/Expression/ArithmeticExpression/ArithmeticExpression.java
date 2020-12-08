package Model.Expression.ArithmeticExpression;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.VariableNotInteger;
import Model.Expression.Expression;
import Model.Type.IntType;
import Model.Type.Type;

import java.util.Map;

public abstract class ArithmeticExpression implements Expression {
    protected final Expression left;
    protected final Expression right;

    protected ArithmeticExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Type typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type type1,type2;
        type1 = left.typeCheck(typeEnv);
        type2 = right.typeCheck(typeEnv);

        if(type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            } else throw new VariableNotInteger("The right expression must be integer");
        } else throw new VariableNotInteger("The left expression must be integer");
    }
}
