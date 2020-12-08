package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.ProgramState;
import Model.Type.Type;

import java.util.Map;

public class NopStatement implements Statement{
    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "noop";
    }
}
