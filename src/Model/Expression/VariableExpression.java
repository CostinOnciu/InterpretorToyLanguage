package Model.Expression;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Type.Type;
import Model.Value.Value;

import java.util.Map;

public class VariableExpression implements Expression{
    private final String variable;

    public VariableExpression(String variable) {
        this.variable = variable;
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable, Map<Integer,Value> heap) throws MyExceptions {
        if(!symbolTable.containsKey(variable))
            throw new UndeclaredVariable(this.toString()+" -> Variable "+variable+" is not declared in this scope");
        return symbolTable.get(variable);
    }

    @Override
    public Type typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        if(typeEnv.containsKey(variable))
            return typeEnv.get(variable);
        else throw new UndeclaredVariable(this.toString()+" -> Variable "+variable+" is not declared in this scope");
    }

    @Override
    public String toString() {
        return variable;
    }
}
