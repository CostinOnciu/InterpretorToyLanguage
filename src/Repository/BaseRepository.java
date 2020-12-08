package Repository;

import Model.ProgramState;

import java.io.IOException;
import java.util.List;

public interface BaseRepository {
    void logProgramStateExec(ProgramState prg) throws IOException;
    void setProgramsList(List<ProgramState> programsList);
    List<ProgramState> getAll();
    void addFork(ProgramState prg);
}
