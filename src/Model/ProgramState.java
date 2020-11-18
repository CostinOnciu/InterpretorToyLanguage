package Model;

import Model.Statement.Statement;
import Model.Value.Value;

import java.io.BufferedReader;
import java.util.*;
import java.util.stream.Collectors;

public class ProgramState {
    private final Stack<Statement> executionStack;
    private final Map<String, Value> symbolTable;
    private final List<Value> outputList;
    private final Map<String, BufferedReader> fileTable;
    private final Map<Integer,Value> heap;

    public ProgramState(Stack<Statement> stack, Map<String, Value> map, List<Value> list, Map<String, BufferedReader> table, Map<Integer, Value> heap){
        executionStack = stack;
        symbolTable = map;
        outputList = list;
        fileTable = table;
        this.heap = heap;
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

    public Map<Integer, Value> getHeap() { return heap; }

    public Map<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        var printableStack = Arrays.asList(executionStack.toArray());
        Collections.reverse(printableStack);
        return "ExeStack:\n" + printableStack.toString() + "\n" +
                "SymTable:\n" + symbolTable.toString() + '\n' +
                "Out:\n" + outputList.toString() +  "\n" +
                "FileTable:" + fileTable.keySet().toString() + '\n' +
                "Heap: " + heap.toString() + '\n';
    }

    public void addNewFile(String fileName,BufferedReader bufferedReader){
        fileTable.put(fileName,bufferedReader);
    }

    public void removeFile(String fileName){
        fileTable.remove(fileName);
    }

    public int newAddress(Value value){
        for(var x:heap.keySet()){
            if(!(heap.containsKey(x+1))) {
                heap.put(x+1,value);
                return x + 1;
            }
        }
        heap.put(1,value);
        return 1;
    }

    public void setHeap(Map<Integer,Value> newHeap){
        heap.clear();
        heap.putAll(newHeap);
    }
}