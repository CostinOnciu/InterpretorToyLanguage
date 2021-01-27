package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;

import java.util.Map;

public class AwaitLatch implements Statement{
    String varName;
    public AwaitLatch(String var) {
        varName=var;
    }

    @Override
    synchronized public ProgramState execute(ProgramState state) throws MyExceptions {
        var symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(varName)){
            var index = ((IntValue)symbolTable.get(varName)).getValue();
            if(state.getLatchTable().containsKey(index)){
                if(state.getLatchTable().get(index)>0){
                    state.getExecutionStack().push(this);
                }
            }
            else throw new MyExceptions("Value of the variable is not a valid index in latch table");
        }
        else
            throw new UndeclaredVariable("Variable "+varName+" is undefined");
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type varType = typeEnv.get(varName);

        if (varType == null)
            throw new UndeclaredVariable("Variable "+ varName + " is undefined");
        else if (!varType.equals(new IntType())){
            throw new MyExceptions("Invalid variable type, it must be integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "awaitLatch(" + varName + ")";
    }
}
