package Model;

import Model.Exceptions.MyExceptions;
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
    private final int id;
    private final int pid;
    private int nextID=1;

    public ProgramState(Stack<Statement> stack, Map<String, Value> map, List<Value> list, Map<String, BufferedReader> table, Map<Integer, Value> heap,int id,int pid){
        executionStack = stack;
        symbolTable = map;
        outputList = list;
        fileTable = table;
        this.heap = heap;
        this.id = id;
        this.pid = pid;
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

    public Map<Integer, Value> getHeap() {
        return heap;
    }

    public Map<String, BufferedReader> getFileTable() {
        return fileTable;
    }

    @Override
    public String toString() {
        var printableStack = Arrays.asList(executionStack.toArray());
        Collections.reverse(printableStack);
        return "ID: " + id + "\n"+
                "PID: " + pid + "\n"+
                "ExeStack:\n" + printableStack.toString() + "\n" +
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

    public Boolean isNotCompleted(){
        return !(executionStack.empty());
    }

    public ProgramState oneStep() throws MyExceptions {
        if(executionStack.empty())
            throw new MyExceptions("Stack is empty");
        Statement statement = executionStack.pop();
        return statement.execute(this);
    }

    public int getID(){
        return id;
    }

    public int newID(){
        nextID++;
        return nextID;
    }
}