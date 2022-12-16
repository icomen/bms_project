package it.unicas.bms_project;

import com.opencsv.bean.CsvToBeanBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static it.unicas.bms_project.InputStartViewController.selectedFile;

public class MainApp extends Application {

    private Stage primaryStage;
    private Stage inputStage;
    private BorderPane rootLayout;
    private BorderPane inputStartView;

    public static int nCells;
    public static int nSensors;
    public static int nModules;
    public static int sampleTime;
    public static String outputPath;
    public static boolean currentMeasurements;
    private static Pane loc;
    private static Pane loc2;

    public static RootLayoutController Rootcontroller;
    public static BmsOverviewController BmsOverviewController;
    public static MeasuresViewController MeasuresController;
    public static List<BmsData> bmsDataList;

    public boolean first = true;


    /**
     * Constructor
     */
    public MainApp() {
    }


    @Override
    public void start(Stage inputStage) {
        this.inputStage = inputStage;
        this.inputStage.setTitle("Welcome BMS App");
        this.inputStage.setResizable(false);

        // Set the application icon.
        this.inputStage.getIcons().add(new Image("file:src/main/resources/images/battery.png"));

        showInputStartView();
        this.inputStage.show();

    }

    public void terminateSession() throws IOException {
        primaryStage.getScene().getWindow().hide();
        MeasuresController.writeOutput();
        start(inputStage);
        inputStage.setHeight(700);
        inputStage.setWidth(450);
    }

    public void setPrimaryStage(Stage primaryStage) throws IOException {
        File file = new File("myfile.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        nCells = Integer.parseInt(reader.readLine());
        nSensors = Integer.parseInt(reader.readLine());
        nModules = Integer.parseInt(reader.readLine());
        sampleTime = Integer.parseInt(reader.readLine());
        outputPath = reader.readLine();
        currentMeasurements = Objects.equals(reader.readLine(), "Current measurements (yes)");
        String name = java.time.LocalDateTime.now() +".txt";
        file.renameTo(new File(name));
        System.out.println("Input data saved in the file: "+name);
        reader.close();


        this.primaryStage = primaryStage;
        this.primaryStage.setResizable(true);
        this.primaryStage.setTitle("BMS App");

        // Set the application icon.
        this.primaryStage.getIcons().add(new Image("file:src/main/resources/images/battery.png"));
        this.primaryStage.setWidth(1120);
        this.primaryStage.setHeight(700);

        initRootLayout();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("MeasuresView.fxml"));
        FXMLLoader loader2 = new FXMLLoader();
        loader2.setLocation(MainApp.class.getResource("BmsOverview.fxml"));

        if(first) {
            loc2 = loader2.load();
            BmsOverviewController = loader2.getController();
            BmsOverviewController.setMainApp(this);
            loc = loader.load();
            MeasuresController = loader.getController();
            MeasuresController.setMainApp(this);
        }
        first = false;
        showBmsOverview();


        try {
            System.out.println("File read");


            bmsDataList = new CsvToBeanBuilder(new FileReader(selectedFile))
                    .withType(BmsData.class)
                    .build()
                    .parse();



        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        this.primaryStage.show();


    }

    /**
     * Initializes the root layout
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
            primaryStage.setScene(scene);



            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                try {
                    handleExit();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


            // Give the controller access to the main app.
            Rootcontroller = loader.getController();
            Rootcontroller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Closes the application.
     */
    public void handleExit() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (Rootcontroller.dm.isSelected()) {
            alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkTheme.css").toString());
        }
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit?");
        alert.getDialogPane().setContent(new CheckBox("Don't ask again"));


        ButtonType buttonTypeOne = new ButtonType("Exit");

        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne){
            MeasuresController.writeOutput();
            System.exit(0);
        }

    }

    public void simpleExit() {
        System.exit(0);

    }

    /**
     * Shows the BMS overview inside the root layout.
     */

    public void showBmsOverview() {
        // Set BMS overview into the center of root layout.
        rootLayout.setCenter(loc2);

    }

    public void showMeasuresView() {

        rootLayout.setCenter(loc);


    }

    public void showSourceView() {
        try {
            // Load BMS overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("SourceView.fxml"));

            // Set BMS overview into the center of root layout.
            rootLayout.setCenter(loader.load());


            // Give the controller access to the main app.
            SourceViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSessionManagerView() {
        try {
            // Load BMS overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("SessionManagerView.fxml"));

            // Set BMS overview into the center of root layout.
            rootLayout.setCenter(loader.load());


            // Give the controller access to the main app.
            SessionManagerController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showInputStartView() {
        try {
            // Load BMS overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("InputStartView.fxml"));
            inputStartView = loader.load();

            // Set BMS overview into the center of root layout.
            //inputStartView.setCenter(loader.load());
            Scene scene = new Scene(inputStartView);
            inputStage.setScene(scene);

            inputStage.setOnCloseRequest(event -> {
                event.consume();
                simpleExit();
            });

            // Give the controller access to the main app.
            InputStartViewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Stage getInputStage() {
        return inputStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

}