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
import java.util.Map;

public class ReadFileStatement implements Statement{
    private final Expression left;
    private final String varName;

    public ReadFileStatement(Expression left, String varName) {
        this.left = left;
        this.varName = varName;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(state.getSymbolTable().containsKey(varName)))
            throw new UndeclaredVariable("Variable "+ varName +" was not declared in this scope");
        if(!(state.getSymbolTable().get(varName).getType().equals(new IntType())))
            throw new VariableNotInteger("Variable "+varName +" should be an integer");
        if(!(left.evaluate(state.getSymbolTable(),state.getHeap()).getType().equals(new StringType())))
            throw new MyExceptions(left.toString()+" -> Expression should return a string value");

        try {
            var line = state.getFileTable().get(left.evaluate(state.getSymbolTable(),state.getHeap()).toString()).readLine();
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
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type expType = left.typeCheck(typeEnv);
        Type varType = typeEnv.get(varName);

        if(varType == null)
            throw new UndeclaredVariable("Variable "+ varName +" was not declared in this scope");
        if(!(varType.equals(new IntType())))
            throw new VariableNotInteger("Variable "+varName +" should be an integer");
        if(expType.equals(new StringType()))
            return typeEnv;
        else throw new MyExceptions(left.toString()+" -> Expression should return a string value");
    }

    @Override
    public String toString() {
        return "ReadFile("+left.toString()+","+varName+")";
    }
}
