package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;

public class PrintStatement implements Statement{
    Expression expression;

    public PrintStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() +")";
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        var stack = state.getExecutionStack();
        var map = state.getSymbolTable();
        var out = state.getOutputList();

        out.add(expression.evaluate(map));
        return state;
    }
}
