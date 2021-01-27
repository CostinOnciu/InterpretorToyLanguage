package Model.Statement;

import Model.Exceptions.InvalidCondition;
import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class IfStatement implements Statement{
    private final Expression expression;
    private final Statement thenStatement;
    private final Statement elseStatement;

    public IfStatement(Expression expression, Statement thenStatement, Statement elseStatement) {
        this.expression = expression;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public Expression getExpression() {
        return expression;
    }

    public Statement getThenStatement() {
        return thenStatement;
    }

    public Statement getElseStatement() {
        return elseStatement;
    }

    @Override
    public String toString() {
        return "IF(" +  expression.toString() + ") THEN " +
                thenStatement.toString() + "ELSE " +
                elseStatement.toString() + "END-IF";
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        var stack = state.getExecutionStack();
        var map = state.getSymbolTable();

        var exp = expression.evaluate(map,state.getHeap());
        if(exp.getType().toString().equals("bool")) {
            BoolValue b = (BoolValue)exp;
            if(b.getValue())
                thenStatement.execute(state);
            else
                elseStatement.execute(state);
        }
        else{
            throw new InvalidCondition(this.toString()+" -> Conditional expression is not a boolean");
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type expType = expression.typeCheck(typeEnv);
        if(expType.equals(new BoolType())){
            thenStatement.typeCheck(typeEnv.entrySet().stream()
                    .map(e -> {
                        try {
                            return new java.util.AbstractMap.SimpleEntry<String, Type>(e.getKey(), Type.copy(e.getValue()));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException noSuchMethodException) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));
            elseStatement.typeCheck(typeEnv.entrySet().stream()
                    .map(e -> {
                        try {
                            return new java.util.AbstractMap.SimpleEntry<String, Type>(e.getKey(), Type.copy(e.getValue()));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException noSuchMethodException) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));
            //thenStatement.typeCheck(typeEnv.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));
            //elseStatement.typeCheck(typeEnv.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));
            return typeEnv;
        }else throw new InvalidCondition(this.toString()+" -> Conditional expression is not a boolean");
    }
}
