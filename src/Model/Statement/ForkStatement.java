package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.ProgramState;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ForkStatement implements Statement{
    private final Statement statement;

    public ForkStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public synchronized ProgramState execute(ProgramState state) throws MyExceptions {
        var exeStack = new Stack<Statement>();
        exeStack.push(statement);

        var symbolTable = state.getSymbolTable().entrySet().stream()
                .map(e -> {
                    try {
                        return new java.util.AbstractMap.SimpleEntry<>(e.getKey(), Value.copy(e.getValue()));
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException noSuchMethodException) {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
        //var sym = new HashMap<String, Value>(state.getSymbolTable());
        return new ProgramState(exeStack,symbolTable,state.getOutputList(),state.getFileTable(),state.getHeap(),state.newID(),state.getID());
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        return statement.typeCheck(typeEnv);
    }

    @Override
    public String toString() {
        return "Fork("+
                statement.toString()+
                ')';
    }
}
