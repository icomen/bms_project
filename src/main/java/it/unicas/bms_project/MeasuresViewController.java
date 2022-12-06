package it.unicas.bms_project;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.tilesfx.tools.FlowGridPane;

public class MeasuresViewController extends FlowGridPane {
    private MainApp mainApp;

    private Gauge gaucheTemperature;

    public static final int TILE_WIDTH = 200;
    public static final int TILE_HEIGHT = 300;


    public MeasuresViewController(int NO_OF_COLS, int NO_OF_ROWS) {
        super(NO_OF_COLS, NO_OF_ROWS);
        gaucheTemperature = GaugeBuilder.create()
                .skinType(Gauge.SkinType.PLAIN_AMP)


                .build();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

}
