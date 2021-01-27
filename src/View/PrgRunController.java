package View;

import Controller.Controller;
import Model.ProgramState;
import Model.Statement.Statement;
import Model.Value.Value;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PrgRunController implements Initializable {

    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,List<Integer>>>, String> semaphoreValue;
    @FXML
    TableView<Map.Entry<Integer, Pair<Integer,List<Integer>>>> semaphoreTable;
    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,List<Integer>>>, String> semaphoreLocation;
    @FXML
    TableColumn<Map.Entry<Integer, Pair<Integer,List<Integer>>>, String> IDs;
    @FXML
    TableColumn<Map.Entry<Integer,Integer>,String> Location;
    @FXML
    TableColumn<Map.Entry<Integer,Integer>,String > Value;
    @FXML
    TableView<Map.Entry<Integer,Integer>> LatchTable;

    Controller myController;
    @FXML
    Label nrPrgStates;
    @FXML
    Button runButton;
    @FXML
    TableView<Map.Entry<Integer, Value>> heapTable;
    @FXML
    TableColumn<HashMap.Entry<Integer,Value>, String> heapTableAddress;
    @FXML
    TableColumn<HashMap.Entry<Integer,Value>, String> heapTableValue;
    @FXML
    ListView<String> outList;
    @FXML
    TableView<HashMap.Entry<String, BufferedReader>> fileTable;
    @FXML
    TableColumn<HashMap.Entry<String, BufferedReader>, String> fileTableFileName;
    @FXML
    ListView<String> prgStateList;
    @FXML
    TableView<HashMap.Entry<String, Value>> symTable;
    @FXML
    TableColumn<HashMap.Entry<String, Value>, String> symTableVariable;
    @FXML
    TableColumn<HashMap.Entry<String, Value>, String> symTableValue;
    @FXML
    ListView<String> exeStackList;

    public PrgRunController(Controller myController) {
        this.myController = myController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialRun();
        prgStateList.setOnMouseClicked(event -> setSymTableAndExeStack());
        runButton.setOnAction(e -> {
            try {
                List<ProgramState> programStateList = myController.removeCompleted(myController.getRepository().getAll());
                myController.collectGarbage();
                myController.oneStepForAll(programStateList);
            } catch (IndexOutOfBoundsException e1) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Toy Language - Current program finished");
                alert.setHeaderText(null);
                alert.setContentText("Program successfully finished!");
                Button confirm = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
                confirm.setDefaultButton(false);
                confirm.setStyle("-fx-focus-color: transparent; -fx-faint-focus-color: transparent;");
                alert.showAndWait();
                Stage stage = (Stage) heapTable.getScene().getWindow();
                stage.close();
                myController.getExecutor().shutdownNow();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            updateUIComponents();
        });
    }

    public void initialRun() {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        prgStateList.getSelectionModel().selectFirst();
        setSymTableAndExeStack();
        setLatchTable();
        setSemaphoreTable();
    }

    private void setSemaphoreTable() {
        ObservableList<Map.Entry<Integer, Pair<Integer,List<Integer>>>> semaphoreTableList = FXCollections.observableArrayList();
        semaphoreLocation.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        IDs.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getValue().toString()));
        semaphoreValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().getKey().toString()));
        try{
            semaphoreTableList.addAll(myController.getRepository().getAll().get(0).getSemaphoreTable().entrySet());
            //System.out.println(myController.getRepository().getAll().get(0).getSemaphoreTable());
        }
        catch (IndexOutOfBoundsException ignored){
        }
        semaphoreTable.setItems(semaphoreTableList);
        semaphoreTable.refresh();
    }

    private void setLatchTable() {
        ObservableList<Map.Entry<Integer,Integer>> latchTableList = FXCollections.observableArrayList();
        Location.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        Value.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        try{
            latchTableList.addAll(myController.getRepository().getAll().get(0).getLatchTable().entrySet());
            //System.out.println(myController.getRepository().getAll().get(0).getLatchTable());
        }
        catch (IndexOutOfBoundsException ignored){
        }
        LatchTable.setItems(latchTableList);
        LatchTable.refresh();
    }

    public void updateUIComponents() {
        setNumberLabel();
        setHeapTable();
        setOutList();
        setFileTable();
        setPrgStateList();
        if(prgStateList.getSelectionModel().getSelectedItem() == null) {
            prgStateList.getSelectionModel().selectFirst();
        }
        setSymTableAndExeStack();
        setLatchTable();
        setSemaphoreTable();
    }

    public void setNumberLabel() {
        nrPrgStates.setText("The number of PrgStates: " + myController.getRepository().getAll().size());
    }

    public void setHeapTable() {
        ObservableList<HashMap.Entry<Integer, Value>> heapTableList = FXCollections.observableArrayList();
        heapTableAddress.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getKey())));
        heapTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
        try {
            heapTableList.addAll(myController.getRepository().getAll().get(0).getHeap().entrySet());
        }
        catch (IndexOutOfBoundsException ignored){
        }
        heapTable.setItems(heapTableList);
    }

    public void setOutList() {
        ObservableList<String> outObservableList = FXCollections.observableArrayList();
        try {
            for (Value e : myController.getRepository().getAll().get(0).getOutputList()) {
                outObservableList.add(e.toString());
            }
        }
        catch (IndexOutOfBoundsException ignored){
        }
        outList.setItems(outObservableList);
    }

    public void setFileTable() {
        ObservableList<HashMap.Entry<String, BufferedReader>> fileTableList = FXCollections.observableArrayList();
        fileTableFileName.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
        try {
            fileTableList.addAll(myController.getRepository().getAll().get(0).getFileTable().entrySet());
        }
        catch (IndexOutOfBoundsException ignored) {
        }

        fileTable.setItems(fileTableList);
    }

    public void setPrgStateList() {
        ObservableList<String> prgStateIdList = FXCollections.observableArrayList();
        for(ProgramState e : myController.getRepository().getAll()) {
            prgStateIdList.add(Integer.toString(e.getID()));
        }
        prgStateList.setItems(prgStateIdList);
    }

    public void setSymTableAndExeStack() {
        ObservableList<HashMap.Entry<String, Value>> symTableList = FXCollections.observableArrayList();
        ObservableList<String> exeStackItemsList = FXCollections.observableArrayList();

        List<ProgramState> allPrgs = myController.getRepository().getAll();
        ProgramState prgResult = null;
        for(ProgramState e : allPrgs) {
            if(e.getID() == Integer.parseInt(prgStateList.getSelectionModel().getSelectedItem())) {
                prgResult = e;
                break;
            }
        }
        if(prgResult != null) {
            symTableList.addAll(prgResult.getSymbolTable().entrySet());
            for(Statement e : prgResult.getExecutionStack()) {
                exeStackItemsList.add(e.toString());
            }
            symTable.setItems(symTableList);
            symTableVariable.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getKey()));
            symTableValue.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getValue().toString()));
            symTable.refresh();
            exeStackList.setItems(exeStackItemsList);
        }
    }

}
