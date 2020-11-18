package Controller;

import Model.Exceptions.IOException;
import Model.Exceptions.MyExceptions;
import Model.ProgramState;
import Model.Statement.Statement;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.ReferenceValue;
import Model.Value.Value;
import Repository.BaseRepository;
import Repository.InMemoryRepository;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Controller {
    private BaseRepository repository;

    public Controller(BaseRepository repository) {
        this.repository = repository;
    }

    ProgramState oneStep(ProgramState state) throws MyExceptions {
        var stack = state.getExecutionStack();
        if(stack.empty())
            //throw new Exception();
            return state;
        Statement statement = stack.pop();

        return statement.execute(state);
    }

    public void allStep(int index) throws MyExceptions{//ProgramState state){
        ProgramState currentState = repository.getCrtPrg();
        var stack = currentState.getExecutionStack();
        var map = new HashMap<Integer,Value>();
        map.put(1,new BoolValue(true));
        map.put(2,new ReferenceValue(0,new IntType()));
        map.put(3,new ReferenceValue(1,new ReferenceType(new BoolType())));
        currentState.setHeap(map);
        while (!stack.empty()) {
            oneStep(currentState);
            System.out.println(currentState.getHeap());
            currentState.setHeap(currentState.getHeap().entrySet().stream().
                    filter(e->{
                        if(!(e.getValue() instanceof ReferenceValue))
                        {
                            var address = e.getKey();
                            var x= currentState.getSymbolTable().values().stream().map(val->{
                                if(val instanceof ReferenceValue)
                                    return val;
                                return null;
                            }).filter(Objects::nonNull).collect(Collectors.toList());
                            return x.stream().map(values->{
                                var y= (ReferenceValue)values;
                                return y.getAddress();
                            }).collect(Collectors.toList()).contains(address);
                        }
                        else{
                            var ref=(ReferenceValue)e.getValue();
                            while(ref.getValueType() instanceof ReferenceValue)
                            {
                                ref = (ReferenceValue) currentState.getHeap().get(ref.getAddress());
                            }
                            return currentState.getHeap().get(ref.getAddress())!=null;
                        }

                    }).
                    collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue)));
            System.out.println(currentState.getHeap());
            System.out.println("\n");
            try {
                repository.logProgramStateExec();
            } catch (java.io.IOException error) {
                throw new IOException(error.getMessage());
            }
        }

        var r = (InMemoryRepository)repository;
        try {
            var logFile = new PrintWriter(new BufferedWriter(new FileWriter(r.getLogFilePath(), true)));
            logFile.flush();
            logFile.write("-----------------------------------------------\n");
            logFile.flush();
        }
        catch (java.io.IOException error){
            throw new IOException("IO error");
        }
    }
}
