package Model;

import Model.Statement.Statement;
import Model.Type.StringType;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileDescriptor;
import java.util.*;

public class ProgramState {
    private Stack<Statement> executionStack;
    private Map<String, Value> symbolTable;
    private List<Value> outputList;
    private Map<String, BufferedReader> fileTable;

    public Map<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    public ProgramState(Stack<Statement> stack, Map<String,Value> map, List<Value> list, Map<String,BufferedReader> table){
        executionStack = stack;
        symbolTable = map;
        outputList = list;
        fileTable = table;
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

    @Override
    public String toString() {
        var printableStack = Arrays.asList(executionStack.toArray());
        Collections.reverse(printableStack);
        return "ExeStack:\n" + printableStack.toString() + "\n" +
                "SymTable:\n" + symbolTable.toString() + '\n' +
                "Out:\n" + outputList.toString() +  "\n" +
                "FileTable:" + fileTable.keySet().toString() + '\n';
    }

    public void addNewFile(String fileName,BufferedReader bufferedReader){
        fileTable.put(fileName,bufferedReader);
    }

    public void removeFile(String fileName){
        fileTable.remove(fileName);
    }
}