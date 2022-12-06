package it.unicas.bms_project;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;

import java.util.Random;

public class BmsOverviewController {
    private MainApp mainApp;
    private static final Random RND = new Random();
    public Tile fireSmokeTile;
    public Gauge batteryGauge;
    public Tile statusTile;

    private Indicator leftGraphics = new Indicator();
    private Indicator middleGraphics = new Indicator();
    private Indicator rightGraphics = new Indicator();

    public static final int TILE_WIDTH = 200;
    public static final int TILE_HEIGHT = 300;

    @FXML
    private GridPane pane;
    @FXML
    public ImageView blackImage;
    @FXML
    public ImageView whiteImage;

    private Gauge gaucheTemperature;



    public BmsOverviewController() {

    }


    @FXML
    private void changeTemperature () {
        double x = RND.nextDouble() * 100;
        fireSmokeTile.setValue(x);
        if (x>60) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            if (MainApp.Rootcontroller.dm.isSelected()) {
                alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkTheme.css").toString());
            }
            alert.setTitle("DANGER");
            alert.setHeaderText("Temperature above 60ºC!!");
            alert.showAndWait();
        }
    }

    @FXML
    private void changeBattery () {
        double x = RND.nextDouble() * 100;
        batteryGauge.setValue(x);
        if (x<10) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            if (MainApp.Rootcontroller.dm.isSelected()) {
                alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkTheme.css").toString());
            }
            alert.setTitle("WARNING");
            alert.setHeaderText("Battery level below 10%");
            alert.showAndWait();
        }

    }

    @FXML
    private void changeAlerts () {
        int r = (int) (RND.nextDouble() * 5);
        int m = (int) (RND.nextDouble() * 5);
        int l = (int) (RND.nextDouble() * 5);
        rightGraphics.setOn(r>0);
        statusTile.setRightValue(r);
        middleGraphics.setOn(m>0);
        statusTile.setMiddleValue(m);
        leftGraphics.setOn(l>0);
        statusTile.setLeftValue(l);
    }


    @FXML
    private void initialize() {
        createTile();
        pane.add(fireSmokeTile, 3, 0);
        whiteImage.setOpacity(1);
        blackImage.setOpacity(0);
        pane.add(batteryGauge, 1, 2);
        pane.add(statusTile, 1, 0);
        pane.add(gaucheTemperature, 3, 2);
    }

    private void createTile() {
        fireSmokeTile = TileBuilder.create().skinType(Tile.SkinType.FIRE_SMOKE)
                .prefSize(TILE_WIDTH+145, TILE_HEIGHT+145)
                .title("Temperature sensor")
                .titleAlignment(TextAlignment.CENTER)
                .unit("\u00b0C")
                .threshold(40) // triggers the fire and smoke effect
                .decimals(2)
                .animated(true)
                .backgroundColor(Color.rgb(255, 255, 255))
                .titleColor(Color.rgb(0, 0, 0))
                .valueColor(Color.rgb(0, 0, 0))
                .unitColor(Color.rgb(0, 0, 0))
                .build();

        batteryGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.BATTERY)
                .animated(true)
                .sectionsVisible(true)
                .sections(new Section(0, 10, Color.rgb(200, 0, 0, 0.8)),
                        new Section(10, 30, Color.rgb(200, 200, 0, 0.8)),
                        new Section(30, 100, Color.rgb(0, 200, 0, 0.8)))
                .foregroundBaseColor(Color.rgb(0, 0, 0))
                .barBackgroundColor(Color.rgb(0, 0, 0))
                .build();

        gaucheTemperature = GaugeBuilder.create()
                .skinType(Gauge.SkinType.PLAIN_AMP)
                .build();

        Paint backgroundPaint = gaucheTemperature.getBackgroundPaint() instanceof Color ? gaucheTemperature.getBackgroundPaint() : Color.BLACK;
        gaucheTemperature.setBackgroundPaint(backgroundPaint);

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
                .prefSize(TILE_WIDTH, TILE_HEIGHT)
                .title("Alerts overview")
                .titleAlignment(TextAlignment.CENTER)
                .leftText("Voltage alert")
                .middleText("Current alert")
                .rightText("Temperature alert")
                .leftGraphics(leftGraphics)
                .middleGraphics(middleGraphics)
                .rightGraphics(rightGraphics)
                .backgroundColor(Color.rgb(255, 255, 255))
                .titleColor(Color.rgb(0, 0, 0))
                .foregroundColor(Color.rgb(0, 0, 0))
                .build();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

}
