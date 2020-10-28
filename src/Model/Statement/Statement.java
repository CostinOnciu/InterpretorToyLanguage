package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.ProgramState;

public interface Statement {
    public ProgramState execute(ProgramState state) throws MyExceptions;
}