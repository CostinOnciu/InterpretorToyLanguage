package Model.Statement;

import Model.Exceptions.InvalidCondition;
import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Value.BoolValue;

public class WhileStatement implements Statement{
    private final Expression expression;
    private final Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(expression.evaluate(state.getSymbolTable(),state.getHeap()).getType().equals(new BoolType())))
            throw new InvalidCondition("Expression needs to return boolean");
        var b = (BoolValue)expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(!(b.equals(new BoolValue(false))))
        {
            var whileStatement = (WhileStatement)this;
            state.getExecutionStack().push(whileStatement);
            state.getExecutionStack().push(statement);
        }
        return state;
    }

    @Override
    public String toString() {
        return "WHILE "+expression.toString()+" DO " +statement.toString() + "END-WHILE";
    }
}
