package Model.Statement;

import Model.Exceptions.AlreadyDeclaredVariable;
import Model.Exceptions.MyExceptions;
import Model.ProgramState;
import Model.Type.*;
import Model.Value.IntValue;
import Model.Value.BoolValue;

public class VarDeclStatement implements Statement {
    Type type;
    String name;

    public VarDeclStatement(Type type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name ;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        var stack = state.getExecutionStack();
        var map = state.getSymbolTable();

        if (map.containsKey(name)){
            throw new AlreadyDeclaredVariable("The variable " + name + " is already declared");
        }

        if(type.toString().equals("bool"))
            map.put(name,new BoolValue(false));
        else
            map.put(name,new IntValue(0));
        return state;
    }
}
