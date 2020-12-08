package Model.Expression.LogicExpression;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.VariableNotBoolean;
import Model.Expression.Expression;
import Model.Type.BoolType;
import Model.Type.Type;

import java.util.Map;

public abstract class LogicExpression implements Expression {
    protected final Expression left;
    protected final Expression right;

    protected LogicExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Type typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type type1,type2;
        type1 = left.typeCheck(typeEnv);
        type2 = right.typeCheck(typeEnv);

        if(type1.equals(new BoolType())){
            if(type2.equals(new BoolType())){
                return new BoolType();
            }else throw new VariableNotBoolean("The right expression must return boolean");
        }else throw new VariableNotBoolean("The left expression must return boolean");
    }
}
