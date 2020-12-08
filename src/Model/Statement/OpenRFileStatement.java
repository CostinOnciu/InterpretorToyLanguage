package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.StringType;
import Model.Type.Type;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;

public class OpenRFileStatement implements Statement{
    private final Expression expression;

    public OpenRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(expression.evaluate(state.getSymbolTable(),state.getHeap()).getType().equals(new StringType())))
            throw new MyExceptions("Filename should be a string");
        if(state.getFileTable().containsKey(expression.evaluate(state.getSymbolTable(),state.getHeap()).toString()))
            throw new MyExceptions("File already opened");
        try {
            var reader = new BufferedReader(new FileReader(expression.evaluate(state.getSymbolTable(),state.getHeap()).toString()));
            state.addNewFile(expression.evaluate(state.getSymbolTable(),state.getHeap()).toString(),reader);
        }
        catch (Exception error)
        {
            throw new MyExceptions(this.toString()+" -> "+error.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type expType = expression.typeCheck(typeEnv);
        if(expType.equals(new StringType()))
            return typeEnv;
        else throw new MyExceptions("Filename should be a string");
    }

    @Override
    public String toString() {
        return "OpenRFile("+expression.toString()+")";
    }
}
