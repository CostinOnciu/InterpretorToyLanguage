package Model.Statement;

import Model.Exceptions.InvalidCondition;
import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class WhileStatement implements Statement{
    private final Expression expression;
    private final Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        if(!(expression.evaluate(state.getSymbolTable(),state.getHeap()).getType().equals(new BoolType())))
            throw new InvalidCondition("Expression needs to return boolean");
        var b = (BoolValue)expression.evaluate(state.getSymbolTable(),state.getHeap());
        if(!(b.equals(new BoolValue(false))))
        {
            var whileStatement = (WhileStatement)this;
            state.getExecutionStack().push(whileStatement);
            state.getExecutionStack().push(statement);
        }
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type expType = expression.typeCheck(typeEnv);
        if(expType.equals(new BoolType())) {
            statement.typeCheck(typeEnv.entrySet().stream()
                    .map(e -> {
                        try {
                            return new java.util.AbstractMap.SimpleEntry<String, Type>(e.getKey(), Type.copy(e.getValue()));
                        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException noSuchMethodException) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));
            //statement.typeCheck(typeEnv.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
            return typeEnv;
        }else throw new InvalidCondition("Expression needs to return boolean");
    }

    @Override
    public String toString() {
        return "WHILE "+expression.toString()+" DO " +statement.toString() + "END-WHILE";
    }
}
