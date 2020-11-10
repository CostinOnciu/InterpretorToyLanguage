package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.StringType;

import java.io.BufferedReader;
import java.io.FileReader;

public class OpenRFileStatement implements Statement{
    private final Expression expression;

    public OpenRFileStatement(Expression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(expression.evaluate(state.getSymbolTable()).getType().equals(new StringType())))
            throw new MyExceptions("Filename should be a string");
        if(state.getFileTable().containsKey(expression.evaluate(state.getSymbolTable()).toString()))
            throw new MyExceptions("File already opened");
        try {
            var reader = new BufferedReader(new FileReader(expression.evaluate(state.getSymbolTable()).toString()));
            state.addNewFile(expression.evaluate(state.getSymbolTable()).toString(),reader);
        }
        catch (Exception error)
        {
            throw new MyExceptions(this.toString()+" -> "+error.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "OpenRFile("+expression.toString()+")";
    }
}
