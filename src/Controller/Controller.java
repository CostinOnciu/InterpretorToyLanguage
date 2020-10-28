package Controller;

import Model.Exceptions.MyExceptions;
import Model.ProgramState;
import Model.Statement.Statement;
import Repository.BaseRepository;

public class Controller {
    BaseRepository repository;

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
        ProgramState currentState = repository.getByIndex(index-1);
        var stack = currentState.getExecutionStack();

        System.out.println(stack);
        System.out.println(currentState.getSymbolTable());
        System.out.println(currentState.getOutputList());

        while (!stack.empty()){
            oneStep(currentState);

            System.out.println(stack);
            System.out.println(currentState.getSymbolTable());
            System.out.println(currentState.getOutputList());
        }
    }
}
