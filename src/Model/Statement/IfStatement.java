package Model.Statement;

import Model.Exceptions.InvalidCondition;
import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Value.BoolValue;

public class IfStatement implements Statement{
    private final Expression expression;
    private final Statement thenStatement;
    private final Statement elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getThenStatement() {
        return thenStatement;
    }

    public Statement getElseStatement() {
        return elseStatement;
    }

    @Override
    public String toString() {
        return "IF(" +  expression.toString() + ") THEN " +
                thenStatement.toString() + "ELSE " +
                elseStatement.toString() + "END-IF";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        var stack = state.getExecutionStack();
        var map = state.getSymbolTable();

        var exp = expression.evaluate(map,state.getHeap());
        if(exp.getType().toString().equals("bool")) {
            BoolValue b = (BoolValue)exp;
            if(b.getValue())
                thenStatement.execute(state);
            else
                elseStatement.execute(state);
        }
        else{
            throw new InvalidCondition(this.toString()+" -> Conditional expression is not a boolean");
        }
        return state;
    }
}
