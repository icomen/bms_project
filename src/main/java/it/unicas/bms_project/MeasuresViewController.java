package it.unicas.bms_project;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Vector;

public class MeasuresViewController{
    private MainApp mainApp;

    public Vector<Module> vector = new Vector<>();

    TabPane tabPane = new TabPane();
    @FXML
    BorderPane borderPane;
    @FXML
    GridPane gridPane;

    public Vector<Tile> statisticalData = new Vector<Tile>();
    private Tab tab = new Tab();
    private GridPane gridPane2 = new GridPane();


    public MeasuresViewController() {

    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    private void createStatisticalDataTile() {
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
        }

    }

    @FXML
    public void initialize() {
        createStatisticalDataTile();
        borderPane.setCenter(tabPane);
        int column=0;
        for (Tile i:statisticalData) {
            gridPane.add(i, column,0);
            column +=2;
        }
        for (int i = 0; i<mainApp.nModules; i++) {
            Module module = new Module(mainApp.nCells, mainApp.nSensors, mainApp.currentMeasurements, mainApp.Rootcontroller.dm.isSelected());
            HBox hBox = new HBox();
            VBox vBox = new VBox();
            GridPane gridPane = new GridPane();
            hBox.getChildren().add(gridPane);
            hBox.getChildren().add(vBox);
            hBox.setFillHeight(true);
            vBox.setFillWidth(true);
            gridPane.setFillWidth(hBox, true);
            hBox.setHgrow(gridPane, Priority.ALWAYS);
            hBox.setHgrow(vBox, Priority.ALWAYS);
            gridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
            gridPane.setVgap(10); //vertical gap in pixels
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            vector.add(i, module);
            Tab tab = new Tab();
            tab.setText("Module "+(i+1));
            tabPane.getTabs().add(tab);
            tab.setContent(gridPane);
            vector.get(i).getData(mainApp.sampleTime, statisticalData);
            vector.get(i).showData(gridPane);
        }
    }

    public void setDarkMode(Color backgroundColor, Color foregroundColor) {
        for (Tile i:statisticalData) {
            i.setBackgroundColor(backgroundColor);
            i.setValueColor(foregroundColor);
            i.setTextColor(foregroundColor);
            i.setTitleColor(foregroundColor);
            i.setDescriptionColor(foregroundColor);
            i.setUnitColor(foregroundColor);
            i.setForegroundColor(foregroundColor);
        }
        for (Module i: vector) {
            i.setDarkMode(backgroundColor, foregroundColor);
        }
    }



}
