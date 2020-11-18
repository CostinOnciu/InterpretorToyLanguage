package Model.Statement;

import Model.Exceptions.AlreadyDeclaredVariable;
import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.Expression.ValueExpression;
import Model.ProgramState;
import Model.Type.ReferenceType;
import Model.Value.ReferenceValue;

public class Halloc implements Statement{
    private final String variableName;
    private final Expression expression;

    public Halloc(String variableName, Expression expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        var symbolTable = state.getSymbolTable();
        if (!symbolTable.containsKey(variableName))
            throw new AlreadyDeclaredVariable("Undeclared variable "+ variableName);
        else if (!(symbolTable.get(variableName).getType() instanceof ReferenceType))
                throw new MyExceptions("Variable " + variableName + "is not of reference type");
        var exp = expression.evaluate(symbolTable,state.getHeap());
        var y = (ReferenceValue)symbolTable.get(variableName);
        if(!(exp.getType().equals(y.getValueType())))
            throw new MyExceptions("Expression has invalid type");
        var x = new AssignStatement(variableName,new ValueExpression(new ReferenceValue(state.newAddress(exp),y.getValueType())));
        x.execute(state);
        return state;
    }

    @Override
    public String toString() {
        return "new("+variableName+","+expression.toString()+")";
    }
}
