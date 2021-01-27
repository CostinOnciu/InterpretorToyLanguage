package Controller;

import Model.Expression.ValueExpression;
import Model.ProgramState;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.ReferenceType;
import Model.Value.BoolValue;
import Model.Value.ReferenceValue;
import Model.Value.Value;
import Repository.BaseRepository;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class Controller {
    private final BaseRepository repository;
    private final ExecutorService executor;

    public Controller(BaseRepository repository, ExecutorService executor) {
        this.repository = repository;
        this.executor = executor;
    }

    public List<ProgramState> removeCompleted(List<ProgramState> inProgramsList){
        return inProgramsList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAll(List<ProgramState> programStateList) throws Exception {
        programStateList.forEach(prg-> {
            try {
                repository.logProgramStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        List<Callable<ProgramState>> callList = programStateList.stream()
                .map(programState -> (Callable<ProgramState>)(programState::oneStep))
                .collect(Collectors.toList());

        List<ProgramState> newProgramsList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        programStateList.addAll(newProgramsList);
        programStateList.forEach(prg-> {
            try {
                repository.logProgramStateExec(prg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        repository.setProgramsList(programStateList);
    }

    public void collectGarbage(){
        List<Integer> addresses = new ArrayList<>();
        repository.getAll().forEach(programState -> {
            var x = programState.getSymbolTable().values().stream()
                    .map(val -> {
                        if(val.getType() instanceof ReferenceType)
                            return ((ReferenceValue)val).getAddress();
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            addresses.addAll(x);
            var y = programState.getHeap().values().stream()
                    .map(val -> {
                        if(val.getType() instanceof ReferenceType) {
                            var exp = new ValueExpression(val);
                            return ((ReferenceValue) exp.evaluate(programState.getSymbolTable(),programState.getHeap())).getAddress();
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            addresses.addAll(y);
        });

        var finalAddresses = addresses.stream().distinct().collect(Collectors.toList());
        //System.out.println(finalAddresses);

        var programStateList = repository.getAll();
        programStateList.get(0).setHeap(programStateList.get(0).getHeap().entrySet().stream()
                .filter(e -> {
                    if(finalAddresses.contains(e.getKey()))
                        return true;
                    return false;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        var x = addresses.stream().map(Object::toString).reduce("", (a,b)->a+b).describeConstable().stream().collect(Collectors.toList());
        //var y = (List<Integer>)new ArrayList<Integer>(x);
        //System.out.println(x);
        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }


    public void allStep() throws Exception {
        List<ProgramState> programStateList = removeCompleted(repository.getAll());
        var map = new HashMap<Integer,Value>();
        map.put(1,new BoolValue(true));
        map.put(2,new ReferenceValue(0,new IntType()));
        map.put(3,new ReferenceValue(1,new ReferenceType(new BoolType())));
        repository.getAll().get(0).setHeap(map);
        while (programStateList.size() > 0){
            collectGarbage();
            oneStepForAll(programStateList);
            programStateList = removeCompleted(repository.getAll());
        }
        executor.shutdownNow();
        repository.setProgramsList(programStateList);
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    @Override
    public String toString() {
        return repository.toString();
    }

    public BaseRepository getRepository(){
        return this.repository;
    }
           /* Set<Integer> usedAddressesSet = currentState.getSymbolTable().values().stream()
                    .filter(value -> value instanceof ReferenceValue)
                    .flatMap(value -> {
                        Stream.Builder<Integer> builder = Stream.builder();
                        while (value instanceof ReferenceValue) {
                            Integer address = ((ReferenceValue) value).getAddress();
                            if (address == 0)
                                break;
                            builder.accept(address);
                            value = currentState.getHeap().get(address);
                        }
                        return builder.build();
                    })
                    .collect(Collectors.toSet());
            Set<Integer> keys = Set.copyOf(currentState.getHeap().keySet());
            keys.stream()
                    .filter(i -> !usedAddressesSet.contains(i))
                    .forEach(i -> currentState.getHeap().remove(i));
            */
}
