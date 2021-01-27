import Controller.Controller;
import Model.Exceptions.MyExceptions;
import Model.Expression.ArithmeticExpression.DifferenceExpression;
import Model.Expression.ArithmeticExpression.SumExpression;
import Model.Expression.ReadHeap;
import Model.Expression.RelationshipExpressions.GreaterExpression;
import Model.Expression.ValueExpression;
import Model.Expression.VariableExpression;
import Model.ProgramState;
import Model.Statement.*;
import Model.Type.IntType;
import Model.Type.ReferenceType;
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
import java.util.concurrent.Executors;

public class Interpreter {
    public static void main(String[] args) {
        Statement ex1 = new CompoundStatement(new VarDeclStatement(new IntType(), "v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        var stack1 = new Stack<Statement>();
        stack1.push(ex1);
        ProgramState prg1 = new ProgramState(stack1, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1,0);
        BaseRepository repo1 = new InMemoryRepository(prg1, "log1.txt");
        Controller ctr1 = new Controller(repo1, Executors.newFixedThreadPool(2));

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
        ProgramState prg2 = new ProgramState(stack2, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1,0);
        BaseRepository repo2 = new InMemoryRepository(prg2, "log2.txt");
        Controller ctr2 = new Controller(repo2, Executors.newFixedThreadPool(2));

        Statement ex3 = new CompoundStatement(new VarDeclStatement(new IntType(), "a"),
                new AssignStatement("a", new ValueExpression(new BoolValue(true))));
        var stack3 = new Stack<Statement>();
        stack3.push(ex3);
        ProgramState prg3 = new ProgramState(stack3, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1,0);
        BaseRepository repo3 = new InMemoryRepository(prg3, "log3.txt");
        Controller ctr3 = new Controller(repo3, Executors.newFixedThreadPool(2));

        Statement ex4 = new CompoundStatement(new VarDeclStatement(new ReferenceType(new IntType()),"v"),
                new CompoundStatement(new Halloc("v",new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new CompoundStatement(new VarDeclStatement(new ReferenceType(new ReferenceType(new IntType())),"w"),
                                        new CompoundStatement(new Halloc("w",new VariableExpression("v")),
                                                new CompoundStatement(new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("w")))),
                                                        new CompoundStatement(new Halloc("v",new ValueExpression(new IntValue(30))),
                                                                new PrintStatement(new SumExpression(new ReadHeap(new ReadHeap(new VariableExpression("w")))
                                                                        ,new ValueExpression(new IntValue(5)))))))))));
        var stack4 = new Stack<Statement>();
        stack4.push(ex4);
        ProgramState prg4 = new ProgramState(stack4, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1,0);
        BaseRepository repo4 = new InMemoryRepository(prg4,"log4.txt");
        Controller ctr4 = new Controller(repo4, Executors.newFixedThreadPool(2));

        Statement ex5 = new CompoundStatement(new VarDeclStatement(new IntType(),"v"),
                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new GreaterExpression(new VariableExpression("v"),new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),new AssignStatement("v",
                                        new DifferenceExpression(new VariableExpression("v"),new ValueExpression(new IntValue(1)))))),
                        new PrintStatement(new VariableExpression("v")))));
        var stack5 = new Stack<Statement>();
        stack5.push(ex5);
        ProgramState prg5 = new ProgramState(stack5, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1,0);
        BaseRepository repo5 = new InMemoryRepository(prg5,"log5.txt");
        Controller ctr5 = new Controller(repo5, Executors.newFixedThreadPool(2));

        Statement ex6 = new CompoundStatement(new VarDeclStatement(new IntType(),"v"),
                new CompoundStatement(new VarDeclStatement(new ReferenceType(new IntType()),"a"),
                        new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new Halloc("a",new ValueExpression(new IntValue(20))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeap("a",new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignStatement("v",new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new ReadHeap(new VariableExpression("a"))))))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new ReadHeap(new VariableExpression("a")))))))));
        var stack6 = new Stack<Statement>();
        stack6.push(ex6);
        ProgramState prg6 = new ProgramState(stack6, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1,0);
        BaseRepository repo6 = new InMemoryRepository(prg6,"log6.txt");
        Controller ctr6 = new Controller(repo6,Executors.newFixedThreadPool(2));

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        try {
            ex1.typeCheck(new HashMap<>());
            menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        }
        catch (MyExceptions exception) {
            System.out.println("\n");
            System.out.println("ERROR FOR EX1 :" + ex1);
            System.out.println("the program won't run because it has the following error");
            System.out.println(exception.toString());
            System.out.println("\n");
        }

        try {
            ex2.typeCheck(new HashMap<>());
            menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        }
        catch (MyExceptions exception) {
            System.out.println("\n");
            System.out.println("ERROR FOR EX2 :" + ex2);
            System.out.println("the program won't run because it has the following error");
            System.out.println(exception.toString());
            System.out.println("\n");
        }

        try {
            ex3.typeCheck(new HashMap<>());
            menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        }
        catch (MyExceptions exception) {
            System.out.println("\n");
            System.out.println("ERROR FOR EX3 :" + ex3);
            System.out.println("the program won't run because it has the following error");
            System.out.println(exception.toString());
            System.out.println("\n");
        }

        try {
            ex4.typeCheck(new HashMap<>());
            menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        }
        catch (MyExceptions exception) {
            System.out.println("\n");
            System.out.println("ERROR FOR EX4 :" + ex4);
            System.out.println("the program won't run because it has the following error");
            System.out.println(exception.toString());
            System.out.println("\n");
        }

        try {
            ex5.typeCheck(new HashMap<>());
            menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        }
        catch (MyExceptions exception) {
            System.out.println("\n");
            System.out.println("ERROR FOR EX5 :" + ex5);
            System.out.println("the program won't run because it has the following error");
            System.out.println(exception.toString());
            System.out.println("\n");
        }

        try {
            ex6.typeCheck(new HashMap<>());
            menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        }
        catch (MyExceptions exception) {
            System.out.println("\n");
            System.out.println("ERROR FOR EX6 :" + ex6);
            System.out.println("the program won't run because it has the following error");
            System.out.println(exception.toString());
            System.out.println("\n");
        }

        menu.show();
    }
}