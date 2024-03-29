package it.unicas.bms_project;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static it.unicas.bms_project.MainApp.nCells;
import static it.unicas.bms_project.MainApp.sampleTime;

public class BmsOverviewController
{

    private MainApp mainApp;
    private static final Random RND = new Random();
    public Tile fireSmokeTile;
    public Gauge batteryGauge;
    public Tile statusTile;

    private final Indicator leftGraphics = new Indicator();
    private final Indicator middleGraphics = new Indicator();
    private final Indicator rightGraphics = new Indicator();

    @FXML
    private HBox hBox;



    public BmsOverviewController()
    {
    }


    private void changeTemperature ()
    {
        double x = Module.maxTemp;
        fireSmokeTile.setValue(x);

        if (x>60)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (MainApp.Rootcontroller.dm.isSelected())
            {
                alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkTheme.css").toString());
            }
            alert.setTitle("DANGER");
            alert.setHeaderText("Temperature above 60ºC!!");
            alert.showAndWait();
        }
    }

    private void changeBattery ()
    {
        double x = (Module.sum/nCells)*100/4.5;
        batteryGauge.setValue(x);
    }

    private void changeAlerts ()
    {
        int r = Module.temperatureFaults;
        Double m = Module.currentFaults;
        int l = Module.voltageFaults;
        rightGraphics.setOn(r>0);
        statusTile.setRightValue(r);
        middleGraphics.setOn(m>0);
        statusTile.setMiddleValue(m);
        leftGraphics.setOn(l>0);
        statusTile.setLeftValue(l);
    }


    @FXML
    private void initialize()
    {
        createTile();

        GridPane gridPane = new GridPane();
        gridPane.add(fireSmokeTile, 1, 0);
        gridPane.add(batteryGauge, 0, 1);
        gridPane.add(statusTile, 0, 0);

        hBox.setAlignment(Pos.TOP_LEFT);
        hBox.getChildren().add(gridPane);
        GridPane.setFillWidth(hBox, true);
        gridPane.setHgap(10); //horizontal gap in pixels => that's what you are asking for
        gridPane.setVgap(10); //vertical gap in pixels
        gridPane.setPadding(new Insets(10, 10, 10, 10));

        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() ->
        {
            Platform.runLater(() ->
            {
                changeAlerts();
                changeBattery();
                changeTemperature();
            });
        }, 0, sampleTime, TimeUnit.SECONDS);
    }

    private void createTile()
    {
        Color backgroundColor;
        Color foregroundColor;
        if (MainApp.Rootcontroller.dm.isSelected())
        {
            backgroundColor = Color.rgb(0, 0, 0);
            foregroundColor = Color.rgb(255, 255, 255);
        }
        else
        {
            foregroundColor = Color.rgb(0, 0, 0);
            backgroundColor = Color.rgb(255, 255, 255);
        }
        fireSmokeTile = TileBuilder.create().skinType(Tile.SkinType.FIRE_SMOKE)
                .prefSize(300, 300)
                .title("Maximum temperature detected")
                .titleAlignment(TextAlignment.CENTER)
                .unit("°C")
                .threshold(40) // triggers the fire and smoke effect
                .decimals(2)
                .animated(true)
                .backgroundColor(backgroundColor)
                .titleColor(foregroundColor)
                .valueColor(foregroundColor)
                .unitColor(foregroundColor)
                .build();

        batteryGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.BATTERY)
                .prefSize(300, 200)
                .value(50)
                .animated(true)
                .sectionsVisible(true)
                .sections(new Section(0, 10, Color.rgb(200, 0, 0, 0.8)),
                        new Section(10, 30, Color.rgb(200, 200, 0, 0.8)),
                        new Section(30, 100, Color.rgb(0, 200, 0, 0.8)))
                .foregroundBaseColor(foregroundColor)
                .barBackgroundColor(foregroundColor)
                .build();

        rightGraphics.setDotOffColor(Tile.GREEN);
        rightGraphics.setDotOnColor(Tile.RED);
        rightGraphics.setOn(false);
        middleGraphics.setDotOffColor(Tile.GREEN);
        middleGraphics.setDotOnColor(Tile.RED);
        middleGraphics.setOn(false);
        leftGraphics.setDotOffColor(Tile.GREEN);
        leftGraphics.setDotOnColor(Tile.RED);
        leftGraphics.setOn(false);
        statusTile = TileBuilder.create()
                .skinType(Tile.SkinType.STATUS)
                .prefSize(400, 200)
                .title("Alerts overview")
                .titleAlignment(TextAlignment.CENTER)
                .leftText("Voltage alert")
                .middleText("Current alert")
                .rightText("Temperature alert")
                .leftGraphics(leftGraphics)
                .middleGraphics(middleGraphics)
                .rightGraphics(rightGraphics)
                .backgroundColor(backgroundColor)
                .titleColor(foregroundColor)
                .foregroundColor(foregroundColor)
                .build();
    }

    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }


}