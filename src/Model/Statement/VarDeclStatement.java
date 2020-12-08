package Model.Statement;

import Model.Exceptions.AlreadyDeclaredVariable;
import Model.Exceptions.MyExceptions;
import Model.ProgramState;
import Model.Type.*;
import Model.Value.IntValue;
import Model.Value.BoolValue;

import java.util.Map;

public class VarDeclStatement implements Statement {
    private final Type type;
    private final String name;

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
            throw new AlreadyDeclaredVariable(this.toString()+" -> The variable " + name + " is already declared");
        }
        map.put(name,type.defaultValue());
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        typeEnv.put(name,type);
        return typeEnv;
    }
}
