package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.Expression.ValueExpression;
import Model.ProgramState;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;

import java.util.Map;

public class NewLatch implements Statement{
    private final String variableName;
    private final Expression expression;

    public NewLatch(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    synchronized public ProgramState execute(ProgramState state) throws MyExceptions {
        var latchTable = state.getLatchTable();
        var symbolTable = state.getSymbolTable();

        if(!symbolTable.containsKey(variableName)) {
            var x = new VarDeclStatement(new IntType(), variableName);
            x.execute(state);
        }
        var y = new AssignStatement(variableName,new ValueExpression(new IntValue(state.getFreeLatchAddress())));
        y.execute(state);

        try {
            var w = (IntValue) state.getSymbolTable().get(variableName);
            latchTable.put(w.getValue(), ((IntValue) expression.evaluate(state.getSymbolTable(), state.getHeap())).getValue());
        }
        catch (ClassCastException exception) {
            throw new MyExceptions("Invalid expression (must return integer) or variable (must be IntType)");
        }
        //System.out.println(latchTable);
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type varType = typeEnv.get(variableName);
        if (varType == null) {
            typeEnv.put(variableName, new IntType());
            Type expType = expression.typeCheck(typeEnv);
            if (expType instanceof IntType)
                return typeEnv;
            else throw new MyExceptions("Expression has invalid type");
        }
        else if(varType instanceof IntType) {
            Type expType = expression.typeCheck(typeEnv);
            if (expType instanceof IntType)
                return typeEnv;
            else throw new MyExceptions("Expression has invalid type");
        }else throw new MyExceptions("Variable must be Int");
    }

    @Override
    public String toString() {
        return "newLatch(" + variableName + "," + expression.toString() + ")";
    }
}
