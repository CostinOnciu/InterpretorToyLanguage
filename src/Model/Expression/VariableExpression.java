package Model.Expression;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Value.Value;

import java.util.Map;

public class VariableExpression implements Expression{
    private final String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable) throws MyExceptions {
        if(!symbolTable.containsKey(variable))
            throw new UndeclaredVariable(this.toString()+" -> Variable "+variable+" is not declared in this scope");
        return symbolTable.get(variable);
    }

    @Override
    public String toString() {
        return variable;
    }
}
