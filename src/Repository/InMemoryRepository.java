package Repository;

import Model.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements BaseRepository{
    //private List<ProgramState> list;
    ProgramState prg;
    private String logFilePath;
    //private int length;

    public String getLogFilePath() {
        return logFilePath;
    }

    public InMemoryRepository(ProgramState prg, String logFilePath) {
        //this.list = new ArrayList<ProgramState>();
        //this.length = 0;
        this.prg = prg;
        this.logFilePath = logFilePath;
    }

    /*public InMemoryRepository(List<ProgramState> list,String logFilePath){
        this.list = list;
        this.length = list.toArray().length;
        this.logFilePath = logFilePath;
    }

    @Override
    public List<ProgramState> getAll() {
        return list;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void add(ProgramState state) {
        list.add(state);
        length++;
    }

    @Override
    public ProgramState getByIndex(int i) {
        return list.get(i);
    }*/

    @Override
    public void logProgramStateExec() throws IOException {
        var logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath,true)));
        //logFile.append(getCrtPrg().toString());
        logFile.flush();
        logFile.write(prg.toString()+'\n');
        logFile.flush();
        logFile.close();
    }

    @Override
    public ProgramState getCrtPrg() {
        return prg;
    }
}
