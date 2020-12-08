package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Exceptions.VariableTypeDifferent;
import Model.ProgramState;
import Model.Expression.*;
import Model.Type.Type;
import Model.Value.*;

import java.util.Map;

public class AssignStatement implements Statement{
    private final String name;
    private final Expression value;

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
            Value val = value.evaluate(map,state.getHeap());
            Type type = map.get(name).getType();
            if(val.getType().equals(type)){
                map.replace(name,val);
            }
            else {
                throw new VariableTypeDifferent(this.toString()+" -> Type of expression and type of variable do not match");
            }
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type varType = typeEnv.get(name);
        if(varType == null)
            throw new UndeclaredVariable("Variable " + name + " is not declared in this scope");
        Type expType = value.typeCheck(typeEnv);
        if (varType.equals(expType))
            return typeEnv;
        else throw new VariableTypeDifferent("Type of expression and type of variable do not match");
    }
}

