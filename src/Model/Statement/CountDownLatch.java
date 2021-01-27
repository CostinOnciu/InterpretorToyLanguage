package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;

import java.util.Map;

public class CountDownLatch implements Statement{
    String varName;

    public CountDownLatch(String var) {
        varName=var;
    }

    @Override
    synchronized public ProgramState execute(ProgramState state) throws MyExceptions {
        var symbolTable = state.getSymbolTable();
        if (symbolTable.containsKey(varName)){
            var index = ((IntValue)symbolTable.get(varName)).getValue();
            if(state.getLatchTable().containsKey(index)){
                if(state.getLatchTable().get(index)>0){
                    var x = state.getLatchTable().get(index);
                    state.getLatchTable().put(index,x-1);
                    state.getOutputList().add(new IntValue(state.getID()));
                    System.out.println(state.getID());
                }
            }
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
        return "countDownLatch(" + varName + ")";
    }
}
