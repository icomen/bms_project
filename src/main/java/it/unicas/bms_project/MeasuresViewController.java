package it.unicas.bms_project;

import com.opencsv.CSVWriter;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Vector;

public class MeasuresViewController{
    private MainApp mainApp;

    public Vector<Module> vector = new Vector<>();
    Vector<Indicator> graphics = new Vector<>();
    TabPane tabPane = new TabPane();
    @FXML
    BorderPane borderPane;
    @FXML
    HBox hBox;
    @FXML
    GridPane gridPane;

    public Vector<Tile> statisticalData = new Vector<Tile>();
    public Tile statusTile;


    public MeasuresViewController() {

    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void createStatisticalDataTile(Color backgroundColor, Color foregroundColor) {
        Tile Vmax = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .value(0.0)
                .title("Vmax")
                .textSize(Tile.TextSize.BIGGER)
                .text("Whatever text")
                .unit("V")
                .description("Test")
                .build();

        Tile Vmin = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .value(0.0)
                .title("Vmin")
                .textSize(Tile.TextSize.BIGGER)
                .text("Whatever text")
                .unit("V")
                .description("Test")
                .build();

        Tile Vaverage = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .value(0.0)
                .title("Vaverage")
                .textSize(Tile.TextSize.BIGGER)
                .text("Whatever text")
                .unit("V")
                .build();

        Tile deltaV = TileBuilder.create()
                .skinType(Tile.SkinType.NUMBER)
                .value(0.0)
                .title("deltaV")
                .textSize(Tile.TextSize.BIGGER)
                .text("(Vmax-Vmin)")
                .unit("V")
                .build();

        statisticalData.add(Vmax);
        statisticalData.add(Vmin);
        statisticalData.add(Vaverage);
        statisticalData.add(deltaV);

        for (Tile i:statisticalData) {
            i.setTextVisible(true);
            i.setTextAlignment(TextAlignment.CENTER);
            i.setTitleAlignment(TextAlignment.CENTER);
            i.setBackgroundColor(backgroundColor);
            i.setValueColor(foregroundColor);
            i.setTextColor(foregroundColor);
            i.setTitleColor(foregroundColor);
            i.setDescriptionColor(foregroundColor);
            i.setUnitColor(foregroundColor);
            i.setForegroundColor(foregroundColor);
            i.setMaxWidth(185);
            i.setMinHeight(185);
        }

    }

    private void createFaultsTile(Color backgroundColor, Color foregroundColor){
        Indicator rightGraphics = new Indicator(), middleGraphics = new Indicator(), leftGraphics = new Indicator();
        rightGraphics.setDotOffColor(Tile.GREEN);
        rightGraphics.setDotOnColor(Tile.RED);
        rightGraphics.setOn(false);
        middleGraphics.setDotOffColor(Tile.GREEN);
        middleGraphics.setDotOnColor(Tile.RED);
        middleGraphics.setOn(false);
        leftGraphics.setDotOffColor(Tile.GREEN);
        leftGraphics.setDotOnColor(Tile.RED);
        leftGraphics.setOn(false);
        graphics.add(leftGraphics);
        graphics.add(middleGraphics);
        graphics.add(rightGraphics);
        statusTile = TileBuilder.create()
                .skinType(Tile.SkinType.STATUS)
                .title("Module Alerts")
                .titleAlignment(TextAlignment.CENTER)
                .leftText("Voltage alert")
                .middleText("Current alert")
                .rightText("Temperature\n       alert")
                .leftGraphics(leftGraphics)
                .middleGraphics(middleGraphics)
                .rightGraphics(rightGraphics)
                .backgroundColor(backgroundColor)
                .titleColor(foregroundColor)
                .minHeight(185)
                .minWidth(250)
                .foregroundColor(foregroundColor)
                .build();
    }

    @FXML
    public void initialize() throws IOException {
        boolean isSelected = mainApp.Rootcontroller.dm.isSelected();
        Color backgroundColor, foregroundColor;
        if (isSelected) {
            backgroundColor = Color.rgb(0, 0, 0);
            foregroundColor = Color.rgb(255, 255, 255);
        }
        else {
            foregroundColor = Color.rgb(0, 0, 0);
            backgroundColor = Color.rgb(255, 255, 255);
        }
        createStatisticalDataTile(backgroundColor, foregroundColor);
        createFaultsTile(backgroundColor, foregroundColor);
        borderPane.setCenter(tabPane);
        gridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        gridPane.setVgap(10); //vertical gap in pixels
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        int column=0;
        for (Tile i:statisticalData) {
            gridPane.add(i, column,0);
            column ++;
        }
        gridPane.add(statusTile, column, 0);
        for (int i = 0; i<mainApp.nModules; i++) {
            Module module = new Module(mainApp.nCells, mainApp.nSensors, mainApp.currentMeasurements, mainApp.Rootcontroller.dm.isSelected());
            GridPane gridPane1 = new GridPane();
            hBox.getChildren().add(gridPane1);
            gridPane1.setFillWidth(hBox, true);
            gridPane1.setHgap(10); //horizontal gap in pixels => that's what you are asking for
            gridPane1.setVgap(10); //vertical gap in pixels
            gridPane1.setPadding(new Insets(10, 10, 10, 10));
            vector.add(i, module);
            Tab tab = new Tab();
            tab.setText("Module "+(i+1));
            tabPane.getTabs().add(tab);
            tab.setContent(gridPane1);
            vector.get(i).getData(mainApp.sampleTime, statisticalData, statusTile, graphics);
            vector.get(i).showData(gridPane1);
        }
    }

    public void writeOutput() throws IOException {
        String fileName = "output" + java.time.LocalDateTime.now() + ".csv";
        String path = mainApp.outputPath+ "/" + fileName;
        try (CSVWriter writer = new CSVWriter(new FileWriter(path))) {
            writer.writeAll(vector.get(0).csvData);
        }
        System.out.println("Output file created in: " + path);
    }

    public void setDarkMode(Color backgroundColor, Color foregroundColor) {
        statusTile.setBackgroundColor(backgroundColor);
        statusTile.setForegroundBaseColor(foregroundColor);
        for (Tile i:statisticalData) {
            i.setBackgroundColor(backgroundColor);
            i.setForegroundBaseColor(foregroundColor);
        }
        for (Module i: vector) {
            i.setDarkMode(backgroundColor, foregroundColor);
        }
    }



}
