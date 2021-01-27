package View;

import Controller.Controller;
import Model.Exceptions.MyExceptions;
import Model.Expression.ArithmeticExpression.DifferenceExpression;
import Model.Expression.ArithmeticExpression.MultiplicationExpression;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.concurrent.Executors;

public class PrgListController implements Initializable {
    private final Controller ctr1,ctr2,ctr3,ctr4,ctr5,ctr6,ctr7,ctr8,ctr9,ctr10;
    @FXML
    Button typeCheck;
    @FXML
    ListView<Controller> myPrgList;
    @FXML
    Button runButton;

    public PrgListController()
    {
        Statement ex1 = new CompoundStatement(new VarDeclStatement(new IntType(), "v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(2))),
                        new PrintStatement(new VariableExpression("v"))));
        var stack1 = new Stack<Statement>();
        try{
            ex1.typeCheck(new HashMap<>());
            stack1.push(ex1);
        } catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 1 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }

        ProgramState prg1 = new ProgramState(stack1, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo1 = new InMemoryRepository(prg1, "log1.txt");
        ctr1 = new Controller(repo1, Executors.newFixedThreadPool(2));

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
        try {
            ex2.typeCheck(new HashMap<>());
            stack2.push(ex2);
        }
        catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 2 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }

        ProgramState prg2 = new ProgramState(stack2, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo2 = new InMemoryRepository(prg2, "log2.txt");
        ctr2 = new Controller(repo2, Executors.newFixedThreadPool(2));

        Statement ex3 = new CompoundStatement(new VarDeclStatement(new IntType(), "a"),
                new AssignStatement("a", new ValueExpression(new BoolValue(true))));
        var stack3 = new Stack<Statement>();

        //ex3.typeCheck(new HashMap<>());
        stack3.push(ex3);
        /*try {
            ex3.typeCheck(new HashMap<>());
            stack3.push(ex3);
        } catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 3 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }*/

        ProgramState prg3 = new ProgramState(stack3, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo3 = new InMemoryRepository(prg3, "log3.txt");
        ctr3 = new Controller(repo3, Executors.newFixedThreadPool(2));

        Statement ex4 = new CompoundStatement(new VarDeclStatement(new ReferenceType(new IntType()), "v"),
                new CompoundStatement(new Halloc("v", new ValueExpression(new IntValue(20))),
                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                new CompoundStatement(new VarDeclStatement(new ReferenceType(new ReferenceType(new IntType())), "w"),
                                        new CompoundStatement(new Halloc("w", new VariableExpression("v")),
                                                new CompoundStatement(new PrintStatement(new ReadHeap(new ReadHeap(new VariableExpression("w")))),
                                                        new CompoundStatement(new Halloc("v", new ValueExpression(new IntValue(30))),
                                                                new PrintStatement(new SumExpression(new ReadHeap(new ReadHeap(new VariableExpression("w")))
                                                                        , new ValueExpression(new IntValue(5)))))))))));
        var stack4 = new Stack<Statement>();
        try {
            ex4.typeCheck(new HashMap<>());
            stack4.push(ex4);
        } catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 4 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }
        ProgramState prg4 = new ProgramState(stack4, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo4 = new InMemoryRepository(prg4, "log4.txt");
        ctr4 = new Controller(repo4, Executors.newFixedThreadPool(2));

        Statement ex5 = new CompoundStatement(new VarDeclStatement(new IntType(), "v"),
                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(4))),
                        new CompoundStatement(new WhileStatement(new GreaterExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")), new AssignStatement("v",
                                        new DifferenceExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)))))),
                                new PrintStatement(new VariableExpression("v")))));
        var stack5 = new Stack<Statement>();
        try {
            ex5.typeCheck(new HashMap<>());
            stack5.push(ex5);
        } catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 5 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }
        ProgramState prg5 = new ProgramState(stack5, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo5 = new InMemoryRepository(prg5, "log5.txt");
        ctr5 = new Controller(repo5, Executors.newFixedThreadPool(2));

        Statement ex6 = new CompoundStatement(new VarDeclStatement(new IntType(), "v"),
                new CompoundStatement(new VarDeclStatement(new ReferenceType(new IntType()), "a"),
                        new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(10))),
                                new CompoundStatement(new Halloc("a", new ValueExpression(new IntValue(20))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new WriteHeap("a", new ValueExpression(new IntValue(30))),
                                                new CompoundStatement(new AssignStatement("v", new ValueExpression(new IntValue(32))),
                                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                                new PrintStatement(new ReadHeap(new VariableExpression("a"))))))),
                                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                        new PrintStatement(new ReadHeap(new VariableExpression("a")))))))));
        var stack6 = new Stack<Statement>();
        try {
            ex6.typeCheck(new HashMap<>());
            stack6.push(ex6);
        }
        catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 6 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }
        ProgramState prg6 = new ProgramState(stack6, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo6 = new InMemoryRepository(prg6, "log6.txt");
        ctr6 = new Controller(repo6,Executors.newFixedThreadPool(2));


        Statement ex7 = new CompoundStatement(new VarDeclStatement(new IntType(),"v"),
                new CompoundStatement(new ForkStatement(new CompoundStatement(new ForkStatement(new PrintStatement(new ValueExpression(new IntValue(10)))),new AssignStatement("v",new ValueExpression(new IntValue(4))))),
                        new ForkStatement(new AssignStatement("v",new ValueExpression(new IntValue(8))))));

        var stack7 = new Stack<Statement>();
        try {
            ex7.typeCheck(new HashMap<>());
            stack7.push(ex7);
        }
        catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 7 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }
        ProgramState prg7 = new ProgramState(stack7, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo7 = new InMemoryRepository(prg7, "log7.txt");
        ctr7 = new Controller(repo7,Executors.newFixedThreadPool(2));


        Statement ex8 = new CompoundStatement(new VarDeclStatement(new ReferenceType(new IntType()),"v1"),
                    new CompoundStatement(new VarDeclStatement(new ReferenceType(new IntType()),"v2"),
                        new CompoundStatement(new VarDeclStatement(new ReferenceType(new IntType()),"v3"),
                            new CompoundStatement(new Halloc("v1",new ValueExpression(new IntValue(2))),
                                    new CompoundStatement(new Halloc("v2",new ValueExpression(new IntValue(3))),
                                            new CompoundStatement(new Halloc("v3",new ValueExpression(new IntValue(4))),
                                                    new CompoundStatement(new NewLatch("var",new ReadHeap(new VariableExpression("v2"))),
                                                            new ForkStatement(new CompoundStatement(new WriteHeap("v1",new MultiplicationExpression(new ReadHeap(new VariableExpression("v1")), new ValueExpression(new IntValue(10)))),
                                                                    new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v1"))),
                                                                            new CompoundStatement(new CountDownLatch("var"),
                                                                                    new ForkStatement(new CompoundStatement(new WriteHeap("v2",new MultiplicationExpression(new ReadHeap(new VariableExpression("v2")), new ValueExpression(new IntValue(10)))),
                                                                                            new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v2"))),
                                                                                                    new CompoundStatement(new CountDownLatch("var"), new ForkStatement(new CompoundStatement(new WriteHeap("v3",new MultiplicationExpression(new ReadHeap(new VariableExpression("v3")), new ValueExpression(new IntValue(10)))),
                                                                                                            new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v3"))),
                                                                                                                    new CompoundStatement(new CountDownLatch("var"), new CompoundStatement(new AwaitLatch("var"),
                                                                                                                            new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),
                                                                                                                                    new CompoundStatement(new CountDownLatch("var"),
                                                                                                                                            new PrintStatement(new ValueExpression(new IntValue(100)))))))))))))))))))))))));
        var stack8 = new Stack<Statement>();
        try {
            ex8.typeCheck(new HashMap<>());
            stack8.push(ex8);
        }
        catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 8 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }
        ProgramState prg8 = new ProgramState(stack8, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo8 = new InMemoryRepository(prg8, "log8.txt");
        ctr8 = new Controller(repo8,Executors.newFixedThreadPool(2));

        Statement ex9 = new CompoundStatement(new VarDeclStatement(new ReferenceType(new IntType()),"v1"),
                new CompoundStatement(new VarDeclStatement(new IntType(),"cnt"),
                        new CompoundStatement(new Halloc("v1",new ValueExpression(new IntValue(1))),
                                new CompoundStatement(new CreateSemaphore("cnt",new ReadHeap(new VariableExpression("v1"))),
                                        new CompoundStatement(new ForkStatement(new CompoundStatement(new Acquire("cnt"),
                                                new CompoundStatement(new WriteHeap("v1",new MultiplicationExpression(new ReadHeap(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),
                                                        new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v1"))),
                                                                new Release("cnt"))))),
                                                new CompoundStatement(new ForkStatement(new CompoundStatement(new Acquire("cnt"),
                                                        new CompoundStatement(new WriteHeap("v1",new MultiplicationExpression(new ReadHeap(new VariableExpression("v1")),new ValueExpression(new IntValue(10)))),
                                                                new CompoundStatement(new WriteHeap("v1",new MultiplicationExpression(new ReadHeap(new VariableExpression("v1")),new ValueExpression(new IntValue(2)))),
                                                                        new CompoundStatement(new PrintStatement(new ReadHeap(new VariableExpression("v1"))),
                                                                        new Release("cnt")))))),
                                                        new CompoundStatement(new Acquire("cnt"),new CompoundStatement(new PrintStatement(new DifferenceExpression(new ReadHeap(new VariableExpression("v1")),new ValueExpression(new IntValue(1)))),
                                                        new Release("cnt")))))))));


        var stack9 = new Stack<Statement>();
        try {
            ex9.typeCheck(new HashMap<>());
            stack9.push(ex9);
        }
        catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 9 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }
        ProgramState prg9 = new ProgramState(stack9, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo9 = new InMemoryRepository(prg9, "log9.txt");
        ctr9 = new Controller(repo9,Executors.newFixedThreadPool(2));


        /*
            int a; int b; int c;
            a=1;b=2;c=5;
            (switch(a*10)
              (case (b*c) : print(a);print(b))
              (case (10) : print(100);print(200))
              (default : print(300)));
            print(300)
         */

        Statement ex10 = new CompoundStatement(new VarDeclStatement(new IntType(),"a"),
                new CompoundStatement(new VarDeclStatement(new IntType(),"b"),
                        new CompoundStatement(new VarDeclStatement(new IntType(),"c"),
                                new CompoundStatement(new AssignStatement("a",new ValueExpression(new IntValue(1))),
                                        new CompoundStatement(new AssignStatement("b",new ValueExpression(new IntValue(2))),
                                                new CompoundStatement(new AssignStatement("c",new ValueExpression(new IntValue(5))),
                                                        new CompoundStatement(new Switch(new MultiplicationExpression(new VariableExpression("a"),new ValueExpression(new IntValue(10))),
                                                                                         new MultiplicationExpression(new VariableExpression("b"),new VariableExpression("c")),
                                                                                         new ValueExpression(new IntValue(10)),
                                                                                         new CompoundStatement(new PrintStatement(new VariableExpression("a")),new PrintStatement(new VariableExpression("b"))),
                                                                                         new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(100))),new PrintStatement(new ValueExpression(new IntValue(200)))),
                                                                                         new CompoundStatement(new PrintStatement(new ValueExpression(new IntValue(300))),new NopStatement())),
                                                                new PrintStatement(new ValueExpression(new IntValue(300))))))))));
        var stack10 = new Stack<Statement>();
        try {
            ex10.typeCheck(new HashMap<>());
            stack10.push(ex10);
        }
        catch (MyExceptions exceptions) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Toy Language - Program 10 is invalid");
            alert.setHeaderText(null);
            alert.setContentText(exceptions.toString());
            Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            confirm.setDefaultButton(false);
            confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
            alert.showAndWait();
        }
        ProgramState prg10 = new ProgramState(stack10, new HashMap<>(), new ArrayList<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), new HashMap<>(), 1, 0);
        BaseRepository repo10 = new InMemoryRepository(prg10, "log10.txt");
        ctr10 = new Controller(repo10,Executors.newFixedThreadPool(2));
    }

    public void setUp() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUp();
        ObservableList<Controller> myData =FXCollections.observableArrayList();
        myData.add(ctr1);
        myData.add(ctr2);
        myData.add(ctr3);
        myData.add(ctr4);
        myData.add(ctr5);
        myData.add(ctr6);
        myData.add(ctr7);
        myData.add(ctr8);
        myData.add(ctr9);
        myData.add(ctr10);

        myPrgList.setItems(myData);
        myPrgList.getSelectionModel().selectFirst();
        runButton.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage programStage = new Stage();
                Parent programRoot;
                Callback<Class<?>, Object> controllerFactory = type -> {
                    if (type == PrgRunController.class)
                        return new PrgRunController(myPrgList.getSelectionModel().getSelectedItem());
                    else {
                        try {
                            return type.newInstance();
                        } catch (Exception e) {
                            System.err.println("Couldn't create controller for " + type.getName());
                            throw new RuntimeException(e);
                        }
                    }
                };
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ProgramLayout.fxml"));
                    fxmlLoader.setControllerFactory(controllerFactory);
                    programRoot = fxmlLoader.load();
                    Scene programScene = new Scene(programRoot);
                    programStage.setTitle("Toy Language - Program running");
                    programStage.setScene(programScene);
                    programStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myPrgList.refresh();
            }
        });

        typeCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int index = myPrgList.getSelectionModel().getSelectedIndex();
                Statement selectedProgram = myPrgList.getItems().get(index).getRepository().getAll().get(0).getExecutionStack().peek();
                try {
                    selectedProgram.typeCheck(new HashMap<>());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "IS PERFECT");
                    alert.show();
                }
                catch (MyExceptions e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.show();
                }
            }
        });
    }

}
