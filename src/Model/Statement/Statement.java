package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.ProgramState;
import Model.Type.Type;

import java.util.Map;

public interface Statement {
    ProgramState execute(ProgramState state) throws MyExceptions;
    Map<String,Type> typeCheck(Map<String,Type> typeEnv) throws MyExceptions;
}