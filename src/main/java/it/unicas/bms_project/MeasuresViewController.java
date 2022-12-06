package it.unicas.bms_project;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.tools.FlowGridPane;
import javafx.scene.paint.Color;

public class MeasuresViewController extends FlowGridPane {
    private MainApp mainApp;

    public Gauge gaucheTemperature;


    public MeasuresViewController(int NO_OF_COLS, int NO_OF_ROWS) {
        super(NO_OF_COLS, NO_OF_ROWS);
        gaucheTemperature = GaugeBuilder.create()
                .skinType(Gauge.SkinType.PLAIN_AMP)
                .sectionsVisible(true)
                .sections(new Section(0, 40, Color.rgb(0, 200, 0, 0.8)),
                        new Section(40, 60, Color.rgb(200, 200, 0, 0.8)),
                        new Section(60, 100, Color.rgb(200, 0, 0, 0.8)))
                .build();
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

}
