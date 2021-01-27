package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.ReferenceType;
import Model.Type.Type;
import Model.Value.ReferenceValue;

import java.util.Map;

public class WriteHeap implements Statement{
    private final String variableName;
    private final Expression expression;

    public WriteHeap(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(state.getSymbolTable().containsKey(variableName)))
            throw new UndeclaredVariable("Variable "+variableName+ " is not declared");
        if(!(state.getSymbolTable().get(variableName).getType() instanceof ReferenceType))
            throw new MyExceptions("Variable should be an reference");
        var x = (ReferenceValue)state.getSymbolTable().get(variableName);
        if(!(state.getHeap().containsKey(x.getAddress())))
            throw new MyExceptions("Invalid address it doesn't refer to heap anymore");

        if(!(x.getValueType().equals(expression.evaluate(state.getSymbolTable(),state.getHeap()).getType())))
            throw new MyExceptions("Invalid expression type");

        state.getHeap().replace(x.getAddress(),expression.evaluate(state.getSymbolTable(),state.getHeap()));

        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type expType = expression.typeCheck(typeEnv);
        Type varType = typeEnv.get(variableName);

        if (varType==null)
            throw new UndeclaredVariable("Variable "+variableName+ " is not declared");
        if (varType instanceof ReferenceType){
            if(expType.equals(((ReferenceType) varType).getInnerType())){
                return typeEnv;
            }else throw new MyExceptions("Invalid expression type");
        }else throw new MyExceptions("Variable should be an reference");
    }

    @Override
    public String toString() {
        return "*"+variableName+" = "+expression.toString();
    }
}
