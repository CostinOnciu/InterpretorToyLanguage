package Model.Statement;

import Model.Exceptions.IOException;
import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.StringType;

public class CloseRFileStatement implements Statement{
    private final Expression exp;

    public CloseRFileStatement(Expression exp) {
        this.exp = exp;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(exp.evaluate(state.getSymbolTable()).getType().equals(new StringType())))
            throw new MyExceptions("Expression should return a string value");

        try{
            var x = state.getFileTable().get(exp.evaluate(state.getSymbolTable()).toString());
            x.close();
            state.removeFile(exp.evaluate(state.getSymbolTable()).toString());
        }
        catch (NullPointerException error){
            throw new IOException("File doesn't exist");
        }
        catch (Exception error){
            throw new MyExceptions(this.toString()+" -> "+error.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "CloseRFile("+exp.toString()+")";
    }
}
