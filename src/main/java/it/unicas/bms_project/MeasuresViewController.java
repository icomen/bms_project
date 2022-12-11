package it.unicas.bms_project;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Vector;

public class MeasuresViewController{
    private MainApp mainApp;

    public Vector<Module> vector = new Vector<>();

    @FXML
    TabPane tabPane;



    public MeasuresViewController() {

    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        for (int i = 0; i<mainApp.nModules; i++) {
            Module module = new Module(mainApp.nCells, mainApp.nSensors, mainApp.currentMeasurements, mainApp.Rootcontroller.dm.isSelected());
            GridPane gridPane = new GridPane();
            vector.add(i, module);
            Tab tab = new Tab();
            tab.setText("Module "+(i+1));
            tabPane.getTabs().add(tab);
            tab.setContent(gridPane);
            vector.get(i).getData(mainApp.sampleTime);
            vector.get(i).showData(gridPane);
        }

    }

    public void setDarkMode(Color backgroundColor, Color foregroundColor) {
        for (Module i: vector) {
            i.setDarkMode(backgroundColor, foregroundColor);
        }
    }



}
