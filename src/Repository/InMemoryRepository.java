package Repository;

import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements BaseRepository{
    private final List<ProgramState> list;
    private final String logFilePath;
    //private int length;

    public String getLogFilePath() {
        return logFilePath;
    }

    public InMemoryRepository(ProgramState prg, String logFilePath) {
        this.list = new ArrayList<ProgramState>();
        this.list.add(prg);
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getAll(){
        return list;
    }

    @Override
    public void addFork(ProgramState prg){
        list.add(prg);
    }

    @Override
    public void logProgramStateExec(ProgramState prg) throws IOException {
        var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
        logFile.flush();
        logFile.write(prg.toString()+'\n');
        logFile.flush();
        logFile.close();
    }

    @Override
    public void setProgramsList (List<ProgramState> programsList){
        list.clear();
        list.addAll(programsList);
    }
}
