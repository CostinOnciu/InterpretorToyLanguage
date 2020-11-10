package Controller;

import Model.Exceptions.IOException;
import Model.Exceptions.MyExceptions;
import Model.ProgramState;
import Model.Statement.Statement;
import Repository.BaseRepository;
import Repository.InMemoryRepository;

import javax.imageio.IIOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

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

        while (!stack.empty()) {
            oneStep(currentState);
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
