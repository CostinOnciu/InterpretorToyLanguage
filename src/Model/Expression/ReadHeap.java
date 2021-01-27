package Model.Expression;

import Model.Exceptions.MyExceptions;
import Model.Type.ReferenceType;
import Model.Type.Type;
import Model.Value.ReferenceValue;
import Model.Value.Value;

import java.util.Map;

public class ReadHeap implements Expression{
    private final Expression exp;

    public ReadHeap(Expression exp) {
        this.exp = exp;
    }

    @Override
    public Value evaluate(Map<String, Value> symbolTable, Map<Integer, Value> heap) throws MyExceptions {
        var x = exp.evaluate(symbolTable,heap);
        if(!(x.getType() instanceof ReferenceType))
            throw new MyExceptions("Invalid type of expression");
        var xx = (ReferenceValue)x;
        return heap.get(xx.getAddress());
    }

    @Override
    public Type typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type type = exp.typeCheck(typeEnv);
        if(type instanceof ReferenceType)
            return ((ReferenceType) type).getInnerType();
        else throw new MyExceptions("Invalid type of expression");
    }

    @Override
    public String toString() {
        if(exp instanceof VariableExpression)
            return "*"+exp.toString();
        return "*("+exp.toString()+")";
    }
}
