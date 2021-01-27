package Model.Statement;

import Model.Exceptions.MyExceptions;
import Model.Expression.Expression;
import Model.ProgramState;
import Model.Type.Type;

import java.util.Map;

public class Switch implements Statement{
    private final Expression exp, exp1,exp2;
    private final Statement stmt1,stmt2,stmt3;

    public Switch(Expression exp, Expression exp1, Expression exp2, Statement stmt1, Statement stmt2, Statement stmt3) {
        this.exp = exp;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyExceptions {
        var symbolTable = state.getSymbolTable();
        var heap = state.getHeap();
        if (exp.evaluate(symbolTable,heap).equals(exp1.evaluate(symbolTable,heap)))
            state.getExecutionStack().push(stmt1);
        else if (exp.evaluate(symbolTable,heap).equals(exp2.evaluate(symbolTable,heap)))
            state.getExecutionStack().push(stmt2);
        else state.getExecutionStack().push(stmt3);
        return null;
    }

    @Override
    public Map<String, Type> typeCheck(Map<String, Type> typeEnv) throws MyExceptions {
        Type type,type1,type2;
        type = exp.typeCheck(typeEnv);
        type1 = exp1.typeCheck(typeEnv);
        type2 = exp2.typeCheck(typeEnv);

        if (type.equals(type1)) {
            if (type1.equals(type2)) {
                stmt1.typeCheck(typeEnv);
                stmt2.typeCheck(typeEnv);
                stmt3.typeCheck(typeEnv);
            } else throw new MyExceptions("The type of the second expression is different from the rest");
        } else throw new MyExceptions("The type of the first expression is different from the rest");

        return typeEnv;
    }

    @Override
    public String toString() {
        return "Switch(" + exp + ")"+
                " (case " + exp1 + ")" + "{" + stmt1 + "} " +
                " (exp2 " + exp2 + ")" + "{" + stmt2 + "} " +
                " (default) " + "{" + stmt3 + "}";
    }
}
