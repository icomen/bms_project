package it.unicas.bms_project;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.TileBuilder;
import eu.hansolo.tilesfx.addons.Indicator;
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
    public Vector<Tile> vectorSensors = new Vector<Tile>();
    public Vector<Tile> vectorCells = new Vector<Tile>();
    public int nSensors;
    public int nCells;
    public boolean current;
    private static final Random RND = new Random();
    private Double maxVoltage = -1000.0;
    private Double minVoltage = + 1000.0;
    private Double averageVoltage;
    private int maxCell;
    private int minCell;
    private Tile currentTile;
    public static Double sum = 0.0;
    public static Double maxTemp= -1000.0;
    public static int voltageFaults = 0;
    public static int temperatureFaults = 0;
    public static Double currentFaults = 0.0;


    private void createTempSensors(Color backgroundColor, Color foregroundColor) {
        Tile aux[] = new Tile[2];
        for (int i = 0; i < nSensors; i++) {
            aux[i] = TileBuilder.create()
                    .skinType(Tile.SkinType.GAUGE)
                    .minWidth(200)
                    .threshold(60)
                    .backgroundColor(backgroundColor)
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

    private void createCurrent(Color backgroundColor, Color foregroundColor) {
            currentTile = TileBuilder.create().skinType(Tile.SkinType.SPARK_LINE)
                    .minWidth(200)
                    .title("Current Plot")
                    .titleAlignment(TextAlignment.CENTER)
                    .unit("A")
                    .backgroundColor(backgroundColor)
                    .titleColor(foregroundColor)
                    .valueColor(foregroundColor)
                    .unitColor(foregroundColor)
                    .build();
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
        if (current) {
            createCurrent(backgroundColor, foregroundColor);
        }
    }



    public void getData(int sampleTime, Vector<Tile> statisticalData, Tile alertTile, Vector<Indicator> graphics) {
        getSensorsData(sampleTime);
        getVoltageData(sampleTime);
        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        if (current) {
            getCurrentData(sampleTime);
        }

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
                alertTile.setLeftValue(voltageFaults);
                graphics.get(0).setOn(voltageFaults>0);
                alertTile.setMiddleValue(currentFaults);
                graphics.get(1).setOn(currentFaults>0);
                alertTile.setRightValue(temperatureFaults);
                graphics.get(2).setOn(temperatureFaults>0);
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
                maxTemp = -1000.0;
                temperatureFaults = 0;
                for (Tile i:vectorSensors) {
                    int onesUT = bmsDataList.get(ref.n).getUT().length() - bmsDataList.get(ref.n).getUT().replaceAll("1", "").length();
                    int onesOT = bmsDataList.get(ref.n).getOT().length() - bmsDataList.get(ref.n).getOT().replaceAll("1", "").length();
                    temperatureFaults = (onesOT + onesUT);
                    aux[0][0] = bmsDataList.get(ref.n).getTemp().get("Temp1").iterator().next();
                    aux[0][1] = bmsDataList.get(ref.n).getTemp().get("Temp2").iterator().next();
                    double x = aux[0][vectorSensors.indexOf(i)];
                    i.setValue(x);
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
                voltageFaults = 0;
                for (Tile i:vectorCells) {
                    int onesUV = bmsDataList.get(ref.n).getUV().length() - bmsDataList.get(ref.n).getUV().replaceAll("1", "").length();
                    int onesOV = bmsDataList.get(ref.n).getOV().length() - bmsDataList.get(ref.n).getOV().replaceAll("1", "").length();
                    voltageFaults = (onesOV + onesUV);
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

    private void getCurrentData(int sampleTime) {
        ScheduledExecutorService scheduledExecutorService;
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        var ref = new Object() {
            int n = 0;
        };

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                currentFaults = 0.0;

                currentTile.setValue(bmsDataList.get(ref.n).getI());
                currentFaults = bmsDataList.get(ref.n).getA() + bmsDataList.get(ref.n).getW();

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
        if (current) {
            pane.add(currentTile, 3, 0);
        }
    }

    public void setDarkMode(Color backgroundColor, Color foregroundColor) {
        for (Tile i: vectorSensors) {
            i.setBackgroundColor(backgroundColor);
            i.setForegroundBaseColor(foregroundColor);
        }
        for (Tile i: vectorCells) {
            i.setBackgroundColor(backgroundColor);
            i.setForegroundBaseColor(foregroundColor);
        }
        if (current) {
            currentTile.setBackgroundColor(backgroundColor);
            currentTile.setForegroundBaseColor(foregroundColor);
        }
    }

}
