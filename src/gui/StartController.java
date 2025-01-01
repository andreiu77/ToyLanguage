package gui;

import importprgs.Programs;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.statements.IStatement;

public class StartController implements IGUIController{
    private Stage startWindow;
    private ListView<String> programsList;
    private Button startButton;

    public StartController(Stage window) {
        this.startWindow = window;
        initialize();
    }

    private void initialize(){
        startWindow.setTitle("Selection Window");

        programsList = new ListView<>();
        programsList.getItems().addAll(Programs.getPrgStates().stream().map(Object::toString).toList());
        programsList.setFixedCellSize(25);
        programsList.setPrefHeight(300);

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> {
            if(programsList.getSelectionModel().getSelectedItem() != null) {
                System.out.println(programsList.getSelectionModel().getSelectedItem());
                openMainWindow(Programs.getPrgStates().get(programsList.getSelectionModel().getSelectedIndex()), programsList.getSelectionModel().getSelectedIndex());
            }
        });

        VBox layout = new VBox();
        layout.getChildren().add(programsList);
        layout.getChildren().add(startButton);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().get(1).setStyle("-fx-font-size: 20");
        layout.setAlignment(Pos.CENTER);

        double totalHeight = programsList.getItems().size() * programsList.getFixedCellSize() + 100;

        Scene scene = new Scene(layout, 900, totalHeight);
        startWindow.setScene(scene);
    }

    private void openMainWindow(IStatement statement, int index){
        Stage mainWindow = new Stage();
        MainController mainController = new MainController(mainWindow, statement, index);
    }
}

