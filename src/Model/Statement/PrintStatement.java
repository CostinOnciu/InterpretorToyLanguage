package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.Type;

import java.util.Map;

public class PrintStatement implements Statement{
    private final Expression expression;

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
    public synchronized ProgramState execute(ProgramState state) throws MyExceptions {
        var stack = state.getExecutionStack();
        var map = state.getSymbolTable();
        var out = state.getOutputList();

        out.add(expression.evaluate(map,state.getHeap()));
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        expression.typeCheck(typeEnv);
        return typeEnv;
    }
}
