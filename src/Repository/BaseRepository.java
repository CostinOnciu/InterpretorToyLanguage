package Repository;

import Model.ProgramState;

import java.util.List;

public interface BaseRepository {
    public List<ProgramState> getAll();
    public int getLength();
    public void add(ProgramState state);
    public ProgramState getByIndex(int i);
    //public ProgramState getCrtPrg();
}
