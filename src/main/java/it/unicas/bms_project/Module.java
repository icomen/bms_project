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
    public Gauge gaucheTemperature1;
    public Gauge gaucheTemperature2;
    public Vector<Gauge> vectorSensors = new Vector<Gauge>();
    public Tile cellsVoltage1;
    public Tile cellsVoltage2;
    public Tile cellsVoltage3;
    public Tile cellsVoltage4;
    public Tile cellsVoltage5;
    public Tile cellsVoltage6;
    public Tile cellsVoltage7;
    public Tile cellsVoltage8;
    public Vector<Tile> vectorCells = new Vector<Tile>();
    public int nSensors;
    public int nCells;
    public boolean current;
    private static final Random RND = new Random();


    private void createTempSensors(Color backgroundColor, Color foregroundColor) {
        gaucheTemperature1 = GaugeBuilder.create()
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
        vectorSensors.add(gaucheTemperature1);
        if (nSensors == 2) {
            gaucheTemperature2 = GaugeBuilder.create()
                    .skinType(Gauge.SkinType.PLAIN_AMP)
                    .prefWidth(200)
                    .minWidth(200)
                    .sectionsVisible(true)
                    .sections(new Section(0, 40, Color.rgb(0, 200, 0, 0.8)),
                            new Section(40, 60, Color.rgb(200, 200, 0, 0.8)),
                            new Section(60, 100, Color.rgb(200, 0, 0, 0.8)))
                    .ledOn(false)
                    .backgroundPaint(backgroundColor)
                    .foregroundBaseColor(foregroundColor)
                    .build();
            vectorSensors.add(gaucheTemperature2);
        }
    }

    private void createCells(Color backgroundColor, Color foregroundColor) {
            cellsVoltage1 = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                    .minWidth(200)
                    .title("Cell 1 voltage")
                    .titleAlignment(TextAlignment.CENTER)
                    .unit("\u00b0V")
                    .decimals(2)
                    .animated(true)
                    .backgroundColor(backgroundColor)
                    .titleColor(foregroundColor)
                    .valueColor(foregroundColor)
                    .unitColor(foregroundColor)
                    .build();
            vectorCells.add(cellsVoltage1);
            if (nCells>1) {
                cellsVoltage2 = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                        .minWidth(200)
                        .title("Cell 2 voltage")
                        .titleAlignment(TextAlignment.CENTER)
                        .unit("\u00b0V")
                        .decimals(2)
                        .animated(true)
                        .backgroundColor(backgroundColor)
                        .titleColor(foregroundColor)
                        .valueColor(foregroundColor)
                        .unitColor(foregroundColor)
                        .build();
                vectorCells.add(cellsVoltage2);
                if (nCells>2) {
                    cellsVoltage3 = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                            .minWidth(200)
                            .title("Cell 3 voltage")
                            .titleAlignment(TextAlignment.CENTER)
                            .unit("\u00b0V")
                            .decimals(2)
                            .animated(true)
                            .backgroundColor(backgroundColor)
                            .titleColor(foregroundColor)
                            .valueColor(foregroundColor)
                            .unitColor(foregroundColor)
                            .build();
                    vectorCells.add(cellsVoltage3);
                    if (nCells>3) {
                        cellsVoltage4 = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                                .minWidth(200)
                                .title("Cell 4 voltage")
                                .titleAlignment(TextAlignment.CENTER)
                                .unit("\u00b0V")
                                .decimals(2)
                                .animated(true)
                                .backgroundColor(backgroundColor)
                                .titleColor(foregroundColor)
                                .valueColor(foregroundColor)
                                .unitColor(foregroundColor)
                                .build();
                        vectorCells.add(cellsVoltage4);
                        if (nCells>4) {
                            cellsVoltage5 = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                                    .minWidth(200)
                                    .title("Cell 5 voltage")
                                    .titleAlignment(TextAlignment.CENTER)
                                    .unit("\u00b0V")
                                    .decimals(2)
                                    .animated(true)
                                    .backgroundColor(backgroundColor)
                                    .titleColor(foregroundColor)
                                    .valueColor(foregroundColor)
                                    .unitColor(foregroundColor)
                                    .build();
                            vectorCells.add(cellsVoltage5);
                            if (nCells>5) {
                                cellsVoltage6 = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                                        .minWidth(200)
                                        .title("Cell 6 voltage")
                                        .titleAlignment(TextAlignment.CENTER)
                                        .unit("\u00b0V")
                                        .decimals(2)
                                        .animated(true)
                                        .backgroundColor(backgroundColor)
                                        .titleColor(foregroundColor)
                                        .valueColor(foregroundColor)
                                        .unitColor(foregroundColor)
                                        .build();
                                vectorCells.add(cellsVoltage6);
                                if (nCells>6) {
                                    cellsVoltage7 = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                                            .minWidth(200)
                                            .title("Cell 7 voltage")
                                            .titleAlignment(TextAlignment.CENTER)
                                            .unit("\u00b0V")
                                            .decimals(2)
                                            .animated(true)
                                            .backgroundColor(backgroundColor)
                                            .titleColor(foregroundColor)
                                            .valueColor(foregroundColor)
                                            .unitColor(foregroundColor)
                                            .build();
                                    vectorCells.add(cellsVoltage7);
                                    if (nCells>7) {
                                        cellsVoltage8 = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                                                .minWidth(200)
                                                .title("Cell 8 voltage")
                                                .titleAlignment(TextAlignment.CENTER)
                                                .unit("\u00b0V")
                                                .decimals(2)
                                                .animated(true)
                                                .backgroundColor(backgroundColor)
                                                .titleColor(foregroundColor)
                                                .valueColor(foregroundColor)
                                                .unitColor(foregroundColor)
                                                .build();
                                        vectorCells.add(cellsVoltage8);
                                    }
                                }
                            }
                        }
                    }
                }
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



    public void getData(int sampleTime) {
        getSensorsData(sampleTime);
        getVoltageData(sampleTime);
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
