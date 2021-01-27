package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Exceptions.UndeclaredVariable;
import Model.Expression.Expression;
import Model.Expression.ValueExpression;
import Model.ProgramState;
import Model.Type.ReferenceType;
import Model.Type.Type;
import Model.Value.ReferenceValue;

import java.util.Map;

public class Halloc implements Statement{
    private final String variableName;
    private final Expression expression;

    public Halloc(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) throws MyExceptions {
        var symbolTable = state.getSymbolTable();
        if (!symbolTable.containsKey(variableName))
            throw new UndeclaredVariable("Undeclared variable "+ variableName);
        else if (!(symbolTable.get(variableName).getType() instanceof ReferenceType))
                throw new MyExceptions("Variable " + variableName + "is not of reference type");
        var exp = expression.evaluate(symbolTable,state.getHeap());
        var y = (ReferenceValue)symbolTable.get(variableName);
        if(!(exp.getType().equals(y.getValueType())))
            throw new MyExceptions("Expression has invalid type");
        var x = new AssignStatement(variableName,new ValueExpression(new ReferenceValue(state.newAddress(exp),y.getValueType())));
        x.execute(state);
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type varType = typeEnv.get(variableName);
        if(varType == null)
            throw new UndeclaredVariable("Undeclared variable "+ variableName);
        if(varType instanceof ReferenceType) {
            Type expType = expression.typeCheck(typeEnv);
            if (expType.equals(((ReferenceType)varType).getInnerType()))
                return typeEnv;
            else throw new MyExceptions("Expression has invalid type");
        }else throw new MyExceptions("Variable must reference");
    }

    @Override
    public String toString() {
        return "new("+variableName+","+expression.toString()+")";
    }
}
