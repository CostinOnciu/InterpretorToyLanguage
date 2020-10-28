package Model;

import Model.Statement.Statement;
import Model.Value.Value;

import java.util.*;

public class ProgramState {
    Stack<Statement> executionStack;
    Map<String, Value> symbolTable;
    List<Value> outputList;

    public ProgramState(Stack<Statement> stack,Map<String,Value> map,List<Value> list){
        executionStack = stack;
        symbolTable = map;
        outputList = list;
    }

    public Stack<Statement> getExecutionStack() {
        return executionStack;
    }

    public Map<String, Value> getSymbolTable() {
        return symbolTable;
    }

    public List<Value> getOutputList() {
        return outputList;
    }
}