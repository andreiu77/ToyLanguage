package gui;

import controller.Controller;
import controller.IController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.adt.*;
import model.state.PrgState;
import model.statements.CompStatement;
import model.statements.IStatement;
import model.value.IValue;
import model.value.StringValue;
import repository.IRepository;
import repository.Repository;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class MainController implements IGUIController {
    IStatement startingStatement;
    int index;
    IRepository repo;
    IController ctr;

    Stage mainWindow;
    Label numberOfProgramStates;
    TableView<TableItem> heapTable;
    ListView<String> outputList;
    ListView<StringValue> fileTable;
    ListView<Integer> prgIdList;
    int lastSelectedId;
    Label symTableTitle;
    TableView<TableItem> symTable;
    ListView<String> execStackList;
    Label execStackTitle;

    public MainController(Stage mainWindow, IStatement statement, int index){
        this.startingStatement = statement;
        this.index = index;
        this.mainWindow = mainWindow;
        lastSelectedId = -1;
        initialize();
    }

    private void initialize() {
        //create program
        PrgState prg;
        if((prg = createProgram()) == null){
            return;
        }
        repo = new Repository("log" + index + ".txt");  //not index+1 because first program won't run
        repo.addPrgState(prg);
        ctr = new Controller(repo, true);

        mainWindow.setTitle("Main Window");
        VBox root = new VBox(5);

        numberOfProgramStates = new Label();
        numberOfProgramStates.setMaxWidth(500);
        numberOfProgramStates.setStyle("-fx-font-size: 20");

        HBox middle = new HBox(5);

        //heap table
        VBox heapLayout = new VBox(10);
        heapLayout.setPadding(new Insets(10));
        heapLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label heapTitle = new Label("Heap");
        heapTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");

        heapTable = new TableView<>();
        heapTable.setEditable(false);
        heapTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        heapTable.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd;");
        TableColumn<TableItem, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("column1"));
        TableColumn<TableItem, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("column2"));
        heapTable.getColumns().addAll(addressColumn, valueColumn);
        heapLayout.getChildren().addAll(heapTitle, heapTable);
        heapLayout.setAlignment(Pos.CENTER);
        middle.getChildren().add(heapLayout);

        //output list
        VBox outputLayout = new VBox(10);
        outputLayout.setPadding(new Insets(10));
        outputLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label outputTitle = new Label("Output");
        outputTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");

        outputList = new ListView<>();
        outputList.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd;");
        outputLayout.getChildren().addAll(outputTitle, outputList);
        outputLayout.setAlignment(Pos.CENTER);
        middle.getChildren().add(outputLayout);

        //file table
        VBox fileTableLayout = new VBox(10);
        fileTableLayout.setPadding(new Insets(10));
        fileTableLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label fileTableTitle = new Label("File Table");
        fileTableTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");

        fileTable = new ListView<>();
        fileTable.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd;");
        fileTableLayout.getChildren().addAll(fileTableTitle, fileTable);
        fileTableLayout.setAlignment(Pos.CENTER);
        middle.getChildren().add(fileTableLayout);

        //program id list
        VBox prgIdLayout = new VBox(10);
        prgIdLayout.setPadding(new Insets(10));
        prgIdLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label prgIdTitle = new Label("Program IDs");
        prgIdTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");

        prgIdList = new ListView<>();
        prgIdList.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd;");
        prgIdLayout.getChildren().addAll(prgIdTitle, prgIdList);
        prgIdLayout.setAlignment(Pos.CENTER);
        middle.getChildren().add(prgIdLayout);
        //for changing the symTable between steps
        prgIdList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            lastSelectedId = newVal != null ? newVal : lastSelectedId;
            changeSymTable(lastSelectedId);
            changeExecStack(lastSelectedId);
        });

        //symbol table
        VBox symTableLayout = new VBox(10);
        symTableLayout.setPadding(new Insets(10));
        symTableLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        symTableTitle = new Label("Symbol Table");
        symTableTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");

        symTable = new TableView<>();
        symTable.setPlaceholder(new Label("Select a program ID"));
        symTable.setEditable(false);
        symTable.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd;");
        symTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn<TableItem, String> varNameColumn = new TableColumn<>("Variable Name");
        varNameColumn.setCellValueFactory(new PropertyValueFactory<>("column1"));
        TableColumn<TableItem, String> varValueColumn = new TableColumn<>("Value");
        varValueColumn.setCellValueFactory(new PropertyValueFactory<>("column2"));
        symTable.getColumns().addAll(varNameColumn, varValueColumn);
        symTableLayout.getChildren().addAll(symTableTitle, symTable);
        symTableLayout.setAlignment(Pos.CENTER);
        middle.getChildren().add(symTableLayout);

        //exec stack
        VBox execStackLayout = new VBox(10);
        execStackLayout.setPadding(new Insets(10));
        execStackLayout.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #ccc; -fx-border-radius: 5; -fx-background-radius: 5;");

        execStackTitle = new Label("Execution Stack");
        execStackTitle.setStyle("-fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: #333;");

        execStackList = new ListView<>();
        execStackList.setPlaceholder(new Label("Select a program ID"));
        execStackList.setStyle("-fx-background-color: #fff; -fx-border-color: #ddd;");
        execStackLayout.getChildren().addAll(execStackTitle, execStackList);
        execStackLayout.setAlignment(Pos.CENTER);
        middle.getChildren().add(execStackLayout);

        Button oneStepButton = getOneStepButton();
        oneStepButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 10 20; -fx-border-radius: 5; -fx-background-radius: 5;");
        oneStepButton.setOnMouseEntered(e -> oneStepButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 10 20; -fx-border-radius: 5; -fx-background-radius: 5;"));
        oneStepButton.setOnMouseExited(e -> oneStepButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 16; -fx-padding: 10 20; -fx-border-radius: 5; -fx-background-radius: 5;"));


        root.getChildren().addAll(numberOfProgramStates, middle, oneStepButton);
        //make the scene for fitting the content
        Scene scene = new Scene(root);
        mainWindow.setScene(scene);
        mainWindow.show();
        //terminate program when window is closed
        mainWindow.setOnCloseRequest(e -> {
            ctr.allStep();
            mainWindow.close();
        });
    }

    private Button getOneStepButton() {
        Button oneStepButton = new Button("One step");
        oneStepButton.setOnAction(e -> {
            try {
                boolean stillWorking = ctr.oneStepForAllPrg(repo.getPrgList());
                refresh();
                if(!stillWorking) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Execution finished");
                    alert.setHeaderText("Execution finished");
                    alert.setContentText("All programs have finished executing");
                    alert.showAndWait();
                    oneStepButton.visibleProperty().setValue(false);
                }

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        });
        return oneStepButton;
    }

    private PrgState createProgram(){
        MyIDictionary<String, IValue> symTable = new MyDictionary<>();
        MyIStack<IStatement> execStack = new MyStack<>();
        MyIList<String> output = new MyList<>();
        MyIDictionary<StringValue, BufferedReader> fileTable = new MyDictionary<>();
        MyIHeap heap = new MyHeap();
        try {
            startingStatement.typeCheck(new MyDictionary<>());
        }
        catch(RuntimeException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Typecheck error");
            alert.setHeaderText("Typecheck error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            return null;
        }
        PrgState prg = new PrgState(symTable, execStack, output, startingStatement, fileTable, heap);
        return prg;
    }

    private void refresh(){
        numberOfProgramStates.setText("Number of program states: " + repo.getPrgList().size());
        heapTable.getItems().clear();
        ObservableList<TableItem> heapItems = FXCollections.observableArrayList();
        for(Integer key : repo.getPrgList().get(0).getHeap().getHeap().keySet()){
            heapItems.add(new TableItem(key.toString(), repo.getPrgList().get(0).getHeap().getHeap().get(key).toString()));
        }
        heapTable.setItems(heapItems);

        outputList.getItems().clear();
        outputList.getItems().addAll(repo.getPrgList().get(0).getOutput().getList());

        fileTable.getItems().clear();
        fileTable.getItems().addAll(repo.getPrgList().get(0).getFileTable().getKeys());

        prgIdList.getItems().clear();
        prgIdList.getItems().addAll(repo.getPrgList().stream().map(PrgState::getId).toList());

        if(lastSelectedId != -1)
            changeSymTable(lastSelectedId);

        if(lastSelectedId != -1)
            changeExecStack(lastSelectedId);
    }

    private void changeSymTable(int id){
        symTableTitle.setText("Symbol Table id " + id);
        ObservableList<TableItem> symTableItems = FXCollections.observableArrayList();
        for(String key : repo.getPrgList().stream().filter(p -> p.getId() == id).findFirst().get().getSymTable().getKeys()){
            symTableItems.add(new TableItem(key, repo.getPrgList().stream().filter(p -> p.getId() == id).findFirst().get().getSymTable().getValue(key).toString()));
        }
        symTable.setItems(symTableItems);
    }

    private void changeExecStack(int id){
        execStackTitle.setText("Execution Stack id " + id);
        ObservableList<String> execStackItems = FXCollections.observableArrayList();
        for(String statement : getStackElems(repo.getPrgList().stream().filter(p -> p.getId() == id).findFirst().get().getExeStack().getStack().stream().toList().reversed())){
            execStackItems.add(statement.toString());
        }
        execStackList.setItems(execStackItems);
    }

    private List<String> getStackElems(List<IStatement> stack){
        //the list always has 2 elements: top and compstatement, example below
        //top: varf = test.in, rest: (openFile(varf);(varc = int;(readFile(varf, varc);(print(varc);(readFile(varf, varc);(print(varc);closeFile(varf)))))))
        //we need to extract each instruction from the rest and make a list of those
        //the list will be: openFile(varf), varc = int, readFile(varf, varc), print(varc), readFile(varf, varc), print(varc), closeFile(varf)
        List<String> result = new ArrayList<>();
        IStatement top = null, rest = null;
        if(stack.size() == 1)
            rest = stack.get(0);
        else {
            top = stack.get(0);
            rest = stack.get(1);
            result.add(top.toString());
        }
        if(top == null)
            result.add("");
        while(rest instanceof CompStatement){
            CompStatement comp = (CompStatement) rest;
            result.add(comp.getFirst().toString());
            rest = comp.getSecond();
        }
        result.add(rest.toString());
        return result;
    }
}
