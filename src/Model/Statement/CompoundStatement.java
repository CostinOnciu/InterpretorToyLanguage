package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.ProgramState;

public class CompoundStatement implements Statement{
    private final Statement leftStatement;
    private final Statement rightStatement;

    public CompoundStatement(Statement leftStatement, Statement rightStatement) {
        this.leftStatement = leftStatement;
        this.rightStatement = rightStatement;
    }

    public String toString(){
        return "("+leftStatement.toString()+";"+rightStatement.toString()+")";
    }
    public ProgramState execute(ProgramState state) throws MyExceptions {
        var stack = state.getExecutionStack();
        stack.push(rightStatement);
        stack.push(leftStatement);
        return state;
    }
}
