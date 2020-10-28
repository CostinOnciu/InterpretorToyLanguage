import Controller.Controller;
import Model.Exceptions.MyExceptions;
import Model.Expression.*;
import Model.Expression.ArithmeticExpression.MultiplicationExpression;
import Model.Expression.ArithmeticExpression.SumExpression;
import Model.Value.*;
import Model.Statement.*;
import Model.Type.*;
import Model.ProgramState;
import Repository.*;
import View.*;

import java.util.*;

public class main {
    public static void main(String[] args) throws MyExceptions {
        BaseRepository repo = new InMemoryRepository();

        var stack1 = new Stack<Statement>();
        stack1.push(new CompoundStatement(new VarDeclStatement(new IntType(),"v"),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v")))));
        var stack2 = new Stack<Statement>();
        stack2.push(new CompoundStatement( new VarDeclStatement(new IntType(),"a"),
                new CompoundStatement(new VarDeclStatement(new IntType(),"b"),
                        new CompoundStatement(new AssignStatement("a", new SumExpression(new ValueExpression(new IntValue(2)),new
                                MultiplicationExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5))))),
                                new CompoundStatement(new AssignStatement("b",new SumExpression(new VariableExpression("a"), new
                                        ValueExpression(new IntValue(1)))), new PrintStatement(new VariableExpression("b")))))));
        var stack3 = new Stack<Statement>();
        stack3.push(new CompoundStatement(new VarDeclStatement(new BoolType(),"a"),
                new CompoundStatement(new VarDeclStatement(new IntType(),"v"),
                        new CompoundStatement(new AssignStatement("a", new ValueExpression(new BoolValue(true))),
                                new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignStatement("v",new ValueExpression(new
                                        IntValue(2))), new AssignStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                        VariableExpression("v")))))));
        var stack4 = new Stack<Statement>();
        stack4.push(new AssignStatement("a",new ValueExpression(new BoolValue(true))));
        var stack5 = new Stack<Statement>();
        stack5.push(new CompoundStatement(new VarDeclStatement(new IntType(),"a"),new AssignStatement("a",new ValueExpression(new BoolValue(true)))));
        ProgramState state1 = new ProgramState(stack1, new HashMap<>(),new ArrayList<>());
        ProgramState state2 = new ProgramState(stack2, new HashMap<>(),new ArrayList<>());
        ProgramState state3 = new ProgramState(stack3, new HashMap<>(),new ArrayList<>());
        ProgramState state4 = new ProgramState(stack4, new HashMap<>(),new ArrayList<>());
        ProgramState state5 = new ProgramState(stack5, new HashMap<>(),new ArrayList<>());
        repo.add(state1);
        repo.add(state2);
        repo.add(state3);
        repo.add(state4);
        repo.add(state5);


        Controller controller = new Controller(repo);
        View view = new View(controller);

        view.run();
    }
}

//620