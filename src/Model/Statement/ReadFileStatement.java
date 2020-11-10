package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Exceptions.VariableNotInteger;
import Model.Expression.Expression;
import Model.Expression.ValueExpression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.io.IOException;

public class ReadFileStatement implements Statement{
    private final Expression left;
    private final String varName;

    public ReadFileStatement(Expression left, String varName) {
        this.left = left;
        this.varName = varName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(state.getSymbolTable().containsKey(varName)))
            throw new UndeclaredVariable("Variable "+ varName +" was not declared in this scope");
        if(!(state.getSymbolTable().get(varName).getType().equals(new IntType())))
            throw new VariableNotInteger("Variable "+varName +" should be an integer");
        if(!(left.evaluate(state.getSymbolTable()).getType().equals(new StringType())))
            throw new MyExceptions(left.toString()+" -> Expression should return a string value");

        try {
            var line = state.getFileTable().get(left.evaluate((state.getSymbolTable())).toString()).readLine();
            Statement exec;
            if(line == null) {
                exec = new AssignStatement(varName, new ValueExpression(new IntType().defaultValue()));
            }else {
                exec = new AssignStatement(varName, new ValueExpression(new IntValue(Integer.parseInt(line))));
            }
            exec.execute(state);
        }
        catch (NullPointerException error){
            throw new MyExceptions("File doesn't exist");
        }
        catch (Exception error){
            throw new MyExceptions(this.toString()+" -> " + error.getMessage());
        }
        return state;
    }

    @Override
    public String toString() {
        return "ReadFile("+left.toString()+","+varName+")";
    }
}
