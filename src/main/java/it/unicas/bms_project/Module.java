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

import static it.unicas.bms_project.MainApp.bmsDataList;

public class Module {
    public Vector<Gauge> vectorSensors = new Vector<Gauge>();
    public Vector<Tile> vectorCells = new Vector<Tile>();
    public int nSensors;
    public int nCells;
    public boolean current;
    private static final Random RND = new Random();
    private Double maxVoltage;
    private Double minVoltage;
    private Double averageVoltage;
    private int maxCell;
    private int minCell;
    private Double sum;
    public static Double maxTemp= -1000.0;


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
                statisticalData.get(0).setDescription("Cell "+maxCell);
                statisticalData.get(1).setValue(vmin);
                statisticalData.get(1).setDescription("Cell "+minCell);
                statisticalData.get(2).setValue(vAverage);
                statisticalData.get(3).setValue(deltaV);

                });
        }, 0, sampleTime, TimeUnit.SECONDS);
    }

    private double getMaxVoltage() {
        return maxVoltage;
    }


    private double getMinVoltage() {
        return minVoltage;
    }

    private double getAverageVoltage() {
        return sum/nCells;
    }

    private void getSensorsData(int sampleTime) {
        double[][] aux = new double[1][2];

        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        var ref = new Object() {
            int n = 0;
        };

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                for (Gauge i:vectorSensors) {
                    aux[0][0] = bmsDataList.get(ref.n).getTemp().get("Temp1").iterator().next();
                    aux[0][1] = bmsDataList.get(ref.n).getTemp().get("Temp2").iterator().next();
                    double x = aux[0][vectorSensors.indexOf(i)];
                    i.setValue(x);
                    i.setLedOn(x > 60);
                    if (x>maxTemp) {
                        maxTemp = x;
                    }
                }
                ref.n++;
                if (ref.n==100) {
                    ref.n = 0;
                }
            });
        }, 0, sampleTime, TimeUnit.SECONDS);
    }

    private void getVoltageData(int sampleTime) {
        double[][] aux = new double[1][8];

        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        var ref = new Object() {
            int n = 0;
        };
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                maxVoltage= -1000.0;
                minVoltage = 1000000000.0;
                sum = 0.0;
                for (Tile i:vectorCells) {
                    aux[0][0] = bmsDataList.get(ref.n).getVcell().get("Vcell1").iterator().next();
                    aux[0][1] = bmsDataList.get(ref.n).getVcell().get("Vcell2").iterator().next();
                    aux[0][2] = bmsDataList.get(ref.n).getVcell().get("Vcell3").iterator().next();
                    aux[0][3] = bmsDataList.get(ref.n).getVcell().get("Vcell4").iterator().next();
                    aux[0][4] = bmsDataList.get(ref.n).getVcell().get("Vcell5").iterator().next();
                    aux[0][5] = bmsDataList.get(ref.n).getVcell().get("Vcell6").iterator().next();
                    aux[0][6] = bmsDataList.get(ref.n).getVcell().get("Vcell7").iterator().next();
                    aux[0][7] = bmsDataList.get(ref.n).getVcell().get("Vcell8").iterator().next();
                    double x = aux[0][vectorCells.indexOf(i)];
                    i.setValue(x);
                    if (x>maxVoltage) {
                        maxVoltage = x;
                        maxCell = vectorCells.indexOf(i) + 1;
                    }
                    if (x<minVoltage) {
                        minVoltage = x;
                        minCell = vectorCells.indexOf(i) + 1;
                    }
                    sum+=x;
                }
                ref.n++;
                if (ref.n==100) {
                    ref.n = 0;
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
