package Model.Statement;

import Model.Exceptions.IOException;
import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.StringType;
import Model.Type.Type;

import java.util.Map;

public class CloseRFileStatement implements Statement{
    private final Expression exp;

    public CloseRFileStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(exp.evaluate(state.getSymbolTable(),state.getHeap()).getType().equals(new StringType())))
            throw new MyExceptions("Expression should return a string value");

        try{
            var x = state.getFileTable().get(exp.evaluate(state.getSymbolTable(),state.getHeap()).toString());
            x.close();
            state.removeFile(exp.evaluate(state.getSymbolTable(),state.getHeap()).toString());
        }
        catch (NullPointerException error){
            throw new IOException("File doesn't exist");
        }
        catch (Exception error){
            throw new MyExceptions(this.toString()+" -> "+error.getMessage());
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type expType = exp.typeCheck(typeEnv);
        if(expType.equals(new StringType()))
            return typeEnv;
        else throw new MyExceptions("Expression should return a string value");
    }

    @Override
    public String toString() {
        return "CloseRFile("+exp.toString()+");";
    }
}
