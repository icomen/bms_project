package it.unicas.bms_project;

// Reference to the main application.


import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXToggleButton;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.skins.GaugeSparkLineTileSkin;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Stop;
import javafx.util.Callback;

public class BmsOverviewController {

    private MainApp mainApp;

    @FXML
    private GridPane bar;

    @FXML
    private Label temp;

    private Tile gaugeSparklineTile;




    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public BmsOverviewController() {
    }

    private void createGaugeSparklineTile() {

        gaugeSparklineTile = TileBuilder.create()
                .skinType(Tile.SkinType.GAUGE_SPARK_LINE)
                .prefSize(300, 300)
                .title("Temperature")

                .animated(true)
                .textVisible(false)
                .averagingPeriod(25)
                .autoReferenceValue(true)


                .barColor(Tile.YELLOW_ORANGE)
                .barBackgroundColor(Color.rgb(255, 255, 255, 0.1))
                .sections(
                        new eu.hansolo.tilesfx.Section(0, 33, Tile.LIGHT_GREEN),
                        new eu.hansolo.tilesfx.Section(33, 67, Tile.YELLOW),
                        new eu.hansolo.tilesfx.Section(67, 100, Tile.LIGHT_RED))
                .sectionsVisible(true)
                .highlightSections(true)
                .strokeWithGradient(true)
                .fixedYScale(false)
                .gradientStops(new Stop(0.0, Tile.LIGHT_GREEN),
                        new Stop(0.33, Tile.LIGHT_GREEN),
                        new Stop(0.33,Tile.YELLOW),
                        new Stop(0.67, Tile.YELLOW),
                        new Stop(0.67, Tile.LIGHT_RED),
                        new Stop(1.0, Tile.LIGHT_RED))

                .valueColor(Color.RED)
                .unit("C")
                .smoothing(true)
                .build();
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        createGaugeSparklineTile();
        bar.add(gaugeSparklineTile, 1, 0);

    }


    /*
    @FXML
    private void changeColor() {
        double progress = bar.getProgress();
        String temperature = String.valueOf(progress*100);
        temp.setText(temperature + "ºC");
        if(progress<0.6) {
            //bar.getStylesheets().clear()
            // bar.getStylesheets().add(getClass().getResource("BarStyle1.css").toString());
        } else if (progress<0.8) {
            bar.getStylesheets().clear();
            bar.getStylesheets().add(getClass().getResource("BarStyle2.css").toString());
        }
        else {
            bar.getStylesheets().clear();
            bar.getStylesheets().add(getClass().getResource("BarStyle3.css").toString());
        }

    }



    @FXML
    private void mover(){
        bar.setProgress(bar.getProgress()+0.1);
        changeColor();
    }

    @FXML
    private void mover2(){
        bar.setProgress(bar.getProgress()-0.1);
        changeColor();
    }

     */


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }



}
