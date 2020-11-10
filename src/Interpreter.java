import Controller.Controller;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Repository.BaseRepository;
import Repository.InMemoryRepository;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

class Interpreter {
    public static void main(String[] args) {
        Statement ex1 = new CompoundStatement(new VarDeclStatement(new IntType(), "v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        var stack1 = new Stack<Statement>();
        stack1.push(ex1);
        ProgramState prg1 = new ProgramState(stack1, new HashMap<>(), new ArrayList<>(), new HashMap<>());
        BaseRepository repo1 = new InMemoryRepository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1);

        Statement ex2 = new CompoundStatement(new VarDeclStatement(new StringType(), "varf"),
                new CompoundStatement(new AssignStatement("varf", new ValueExpression(new StringValue("test.in"))),
                        new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                                new CompoundStatement(new VarDeclStatement(new IntType(), "varc"),
                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                        new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                                                        new CloseRFileStatement(new VariableExpression("varf"))))))))));
        var stack2 = new Stack<Statement>();
        stack2.push(ex2);
        ProgramState prg2 = new ProgramState(stack2, new HashMap<>(), new ArrayList<>(), new HashMap<>());
        BaseRepository repo2 = new InMemoryRepository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2);

        Statement ex3 = new CompoundStatement(new VarDeclStatement(new IntType(), "a"),
                new AssignStatement("a", new ValueExpression(new BoolValue(true))));
        var stack3 = new Stack<Statement>();
        stack3.push(ex3);
        ProgramState prg3 = new ProgramState(stack3, new HashMap<>(), new ArrayList<>(), new HashMap<>());
        BaseRepository repo3 = new InMemoryRepository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.show();
    }
}