package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Exceptions.VariableNotInteger;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;

import java.util.Map;

public class Acquire implements Statement{
    private final String varName;

    public Acquire(String varName) {
        this.varName = varName;
    }

    @Override
    synchronized public ProgramState execute(ProgramState state) throws MyExceptions {
        var symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(varName)){
            var index = ((IntValue)symbolTable.get(varName)).getValue();
            if(state.getSemaphoreTable().containsKey(index)){
                synchronized (state.getSemaphoreTable().get(index)) {
                    if (state.getSemaphoreTable().get(index).getKey() > state.getSemaphoreTable().get(index).getValue().size()) {
                        if (!state.getSemaphoreTable().get(index).getValue().contains(state.getID())) {
                            //System.out.println(state.getSemaphoreTable());
                            state.getSemaphoreTable().get(index).getValue().add(state.getID());
                        }
                    } else {
                        state.getExecutionStack().push(this);
                    }
                }
            }
            else throw new MyExceptions("Value of the variable is not a valid index in semaphore table");
        }
        else
            throw new UndeclaredVariable("Variable "+varName+" is undefined");
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type varType = typeEnv.get(varName);

        if (varType == null)
            throw new UndeclaredVariable("Variable "+ varName+ " is undefined");
        else if (varType.equals(new IntType()))
                return typeEnv;
        throw new VariableNotInteger("Variable "+ varName + " must be integer");
    }

    @Override
    public String toString() {
        return "Acquire(" + varName + ')';
    }
}
