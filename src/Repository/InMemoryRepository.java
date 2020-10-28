package Repository;

import Model.ProgramState;

import java.util.ArrayList;
import java.util.List;

public class InMemoryRepository implements BaseRepository{
    List<ProgramState> list;
    int length;

    public InMemoryRepository() {
        this.list = new ArrayList<ProgramState>();
        this.length = 0;
    }

    public InMemoryRepository(List<ProgramState> list,int length){
        this.list = list;
        this.length = length;
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
    }
}
