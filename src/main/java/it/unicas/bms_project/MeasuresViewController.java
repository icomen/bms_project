package it.unicas.bms_project;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.Vector;

public class MeasuresViewController{
    private MainApp mainApp;

    public Gauge gaucheTemperature;
    public Gauge gaucheTemperature2;
    public Vector<Gauge> vec;
    public int nCells = 1;
    private int nTempSensors = 1;
    private int nModules = 1;


    @FXML
    GridPane pane;


    public MeasuresViewController() {

    }

    @FXML
    public void initialize() {
        gaucheTemperature = GaugeBuilder.create()
                .skinType(Gauge.SkinType.PLAIN_AMP)
                .sectionsVisible(true)
                .sections(new Section(0, 40, Color.rgb(0, 200, 0, 0.8)),
                        new Section(40, 60, Color.rgb(200, 200, 0, 0.8)),
                        new Section(60, 100, Color.rgb(200, 0, 0, 0.8)))
                .ledOn(false)
                .build();
        gaucheTemperature2 = GaugeBuilder.create()
                .skinType(Gauge.SkinType.PLAIN_AMP)
                .sectionsVisible(true)
                .sections(new Section(0, 40, Color.rgb(0, 200, 0, 0.8)),
                        new Section(40, 60, Color.rgb(200, 200, 0, 0.8)),
                        new Section(60, 100, Color.rgb(200, 0, 0, 0.8)))
                .ledOn(false)
                .build();
        for (int i = 0; i<nCells; i++) {
            for (int j = 0; j<nTempSensors; j++) {
                pane.add(gaucheTemperature, i, j);
            }
        }
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

}
