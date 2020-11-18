package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Expression.Expression;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Type.ReferenceType;
import Model.Value.ReferenceValue;

public class WriteHeap implements Statement{
    private String variableName;
    private Expression expression;

    public WriteHeap(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(state.getSymbolTable().containsKey(variableName)))
            throw new UndeclaredVariable("Variable "+variableName+ " is not declared");
        if(!(state.getSymbolTable().get(variableName).getType() instanceof ReferenceType))
            throw new MyExceptions("Variable should be an reference");
        var x = (ReferenceValue)state.getSymbolTable().get(variableName);
        if(!(state.getHeap().containsKey(x.getAddress())))
            throw new MyExceptions("Invalid address it doesn't refer to heap anymore");

        if(!(x.getValueType().equals(expression.evaluate(state.getSymbolTable(),state.getHeap()).getType())))
            throw new MyExceptions("Invalid expression type");

        state.getHeap().replace(x.getAddress(),expression.evaluate(state.getSymbolTable(),state.getHeap()));

        return state;
    }

    @Override
    public String toString() {
        return "*"+variableName+" = "+expression.toString();
    }
}
