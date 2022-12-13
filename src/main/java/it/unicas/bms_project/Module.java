package it.unicas.bms_project;

import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import eu.hansolo.medusa.Section;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.application.Platform;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

import java.util.Random;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Module {
    public Vector<Gauge> vectorSensors = new Vector<Gauge>();
    public Vector<Tile> vectorCells = new Vector<Tile>();
    public int nSensors;
    public int nCells;
    public boolean current;
    private static final Random RND = new Random();


    private void createTempSensors(Color backgroundColor, Color foregroundColor) {
        Gauge aux[] = new Gauge[2];
        for (int i = 0; i < nSensors; i++) {
            aux[i] = GaugeBuilder.create()
                    .skinType(Gauge.SkinType.PLAIN_AMP)
                    .minWidth(200)
                    .sectionsVisible(true)
                    .sections(new Section(0, 40, Color.rgb(0, 200, 0, 0.8)),
                            new Section(40, 60, Color.rgb(200, 200, 0, 0.8)),
                            new Section(60, 100, Color.rgb(200, 0, 0, 0.8)))
                    .ledOn(false)
                    .backgroundPaint(backgroundColor)
                    .foregroundBaseColor(foregroundColor)
                    .build();
            vectorSensors.add(aux[i]);
        }
    }

    private void createCells(Color backgroundColor, Color foregroundColor) {
        Tile aux[] = new Tile[8];
        for (int i = 0; i < nCells; i++) {
            aux[i] = TileBuilder.create().skinType(Tile.SkinType.GAUGE)
                    .minWidth(200)
                    .title("Cell "+(i+1)+ " voltage")
                    .titleAlignment(TextAlignment.CENTER)
                    .unit("V")
                    .backgroundColor(backgroundColor)
                    .threshold(75)
                    .maxValue(100)
                    .titleColor(foregroundColor)
                    .valueColor(foregroundColor)
                    .unitColor(foregroundColor)
                    .build();
            vectorCells.add(aux[i]);
        }
    }
    public Module(int nCells, int nSensors, boolean current, boolean isSelected) {
        this.nSensors = nSensors;
        this.nCells = nCells;
        this.current = current;
        Color backgroundColor, foregroundColor;
        if (isSelected) {
            backgroundColor = Color.rgb(0, 0, 0);
            foregroundColor = Color.rgb(255, 255, 255);
        }
        else {
            foregroundColor = Color.rgb(0, 0, 0);
            backgroundColor = Color.rgb(255, 255, 255);
        }
        createTempSensors(backgroundColor, foregroundColor);
        createCells(backgroundColor, foregroundColor);
    }



    public void getData(int sampleTime, Vector<Tile> statisticalData) {
        getSensorsData(sampleTime);
        getVoltageData(sampleTime);
        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                double vmax = getMaxVoltage();
                double vmin = getMinVoltage();
                double vAverage = getAverageVoltage();
                double deltaV = Math.abs(vmax-vmin);
                statisticalData.get(0).setValue(vmax);
                statisticalData.get(1).setValue(vmin);
                statisticalData.get(2).setValue(vAverage);
                statisticalData.get(3).setValue(deltaV);

                });
        }, 0, sampleTime, TimeUnit.SECONDS);
    }

    private double getMaxVoltage() {
        return RND.nextDouble() * 100;
    }


    private double getMinVoltage() {
        return RND.nextDouble() * 100;
    }

    private double getAverageVoltage() {
        return RND.nextDouble() * 100;
    }

    private void getSensorsData(int sampleTime) {
        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                for (Gauge i:vectorSensors) {
                    double x = RND.nextDouble() * 100;
                    i.setValue(x);
                    i.setLedOn(x > 60);
                }
            });
        }, 0, sampleTime, TimeUnit.SECONDS);
    }

    private void getVoltageData(int sampleTime) {
        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                for (Tile i:vectorCells) {
                    double x = RND.nextDouble() * 100;
                    i.setValue(x);
                }
            });
        }, 0, sampleTime, TimeUnit.SECONDS);
    }


    public void showData(GridPane pane) {
        int col = 1;
        int row = 0;
        for (int i = 0; i<nSensors; i++) {
            pane.add(vectorSensors.get(i), i, 0);
        }
        for (int i = 0; i<nCells; i++) {
            pane.add(vectorCells.get(i), row, col);
            row++;
            if (i==3) {
                row = 0;
                col = 2;
            }
        }
    }

    public void setDarkMode(Color backgroundColor, Color foregroundColor) {
        for (Gauge i: vectorSensors) {
            i.setBackgroundPaint(backgroundColor);
            i.setForegroundBaseColor(foregroundColor);
        }
        for (Tile i: vectorCells) {
            i.setBackgroundColor(backgroundColor);
            i.setForegroundBaseColor(foregroundColor);
        }
    }

}
