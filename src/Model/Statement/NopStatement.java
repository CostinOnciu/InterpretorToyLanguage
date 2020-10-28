package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.ProgramState;

public class NopStatement implements Statement{
    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        return state;
    }
}
