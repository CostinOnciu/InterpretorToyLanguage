package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Exceptions.VariableTypeDifferent;
import Model.ProgramState;
import Model.Expression.*;
import Model.Type.Type;
import Model.Value.*;

public class AssignStatement implements Statement{
    String name;
    Expression value;

    public AssignStatement(String name, Expression value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Expression getValue() {
        return value;
    }

    @Override
    public String toString() { return name + "=" + value.toString(); }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        var stack = state.getExecutionStack();
        var map = state.getSymbolTable();

        if(!map.containsKey(name)){
            throw new UndeclaredVariable("Variable " + name + " is not declared in this scope");
        }
        else{
            Value val = value.evaluate(map);
            Type type = map.get(name).getType();
            if(val.getType().equals(type)){
                map.replace(name,val);
            }
            else {
                throw new VariableTypeDifferent("Type of expression and type of variable do not match");
            }
        }
        return state;
    }
}

