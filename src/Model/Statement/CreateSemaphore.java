package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.VariableNotInteger;
import Model.Expression.Expression;
import Model.Expression.ValueExpression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Map;

public class CreateSemaphore implements Statement{
    private final String varName;
    private final Expression expression;

    public CreateSemaphore(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    synchronized public ProgramState execute(ProgramState state) throws MyExceptions {
        var semaphoreTable = state.getSemaphoreTable();
        var symbolTable = state.getSymbolTable();

        if(!symbolTable.containsKey(varName)) {
            var x = new VarDeclStatement(new IntType(), varName);
            x.execute(state);
        }
        var y = new AssignStatement(varName,new ValueExpression(new IntValue(state.getFreeSemaphoreAddress())));
        y.execute(state);

        try {
            var w = (IntValue) state.getSymbolTable().get(varName);
            semaphoreTable.put(w.getValue(), new Pair<>(((IntValue)expression.evaluate(state.getSymbolTable(),state.getHeap())).getValue(),new ArrayList<>()));
        }
        catch (ClassCastException exception) {
            throw new MyExceptions("Invalid expression (must return integer) or variable (must be IntType)");
        }
        //System.out.println(semaphoreTable);
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type varType = typeEnv.get(varName);

        if (varType == null) {
            typeEnv.put(varName,new IntType());
            Type expType = expression.typeCheck(typeEnv);
            if (!expType.equals(new IntType()))
                throw new MyExceptions("The expression must return integer type");
        }
        else {
            if (varType.equals(new IntType())){
                Type expType = expression.typeCheck(typeEnv);
                if (!expType.equals(new IntType()))
                    throw new MyExceptions("The expression must return integer type");
            }
            else throw new VariableNotInteger("The variable must be integer");
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "CreateSemaphore(" + varName + "," + expression.toString() + ")";
    }
}
