package it.unicas.bms_project;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class InputStartViewController {

    private MainApp mainApp;

    private Stage dialogStage;
    @FXML
    private NumberTextField cells;
    @FXML
    private NumberTextField sensors;
    @FXML
    private NumberTextField modules;
    @FXML
    private NumberTextField sample;

    @FXML
    private Button dir;

    @FXML
    private Label labelSelectedDirectory;

    @FXML
    private MenuButton sourceSelection;

    @FXML
    private MenuItem csv;

    @FXML
    private ChoiceBox<String> choiceBox;

    public static File selectedFile;

    private String[] current_measurements = {"Current measurements (yes)\n", "Current measurements (no)\n"};

    private boolean okClicked = false;

    public static File file;


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    public void initialize() {
        choiceBox.getItems().addAll(current_measurements);
    }



    @FXML
    private void simpleExit() {
        mainApp.simpleExit();

    }

    @FXML
    private void setPrimaryStage() throws IOException {
        mainApp.setPrimaryStage(mainApp.getInputStage());
    }


    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() throws IOException {
        if (isInputValid()) {
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            file = new File("input" + dateFormat.format(date) + ".txt");

            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true))))
            {
                out.println(cells.getText());
                out.println(sensors.getText());
                out.println(modules.getText());
                out.println(sample.getText());
                out.println(labelSelectedDirectory.getText());
                out.println(choiceBox.getValue());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            okClicked = true;
            mainApp.getInputStage().close();
            setPrimaryStage();

        }
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (cells.getText() == null || cells.getText().length() == 0 || Integer.parseInt(cells.getText())>8 || Integer.parseInt(cells.getText())<1) {
            errorMessage += "No valid cells number!\n";
        }

        if (sensors.getText() == null || sensors.getText().length() == 0 || Integer.parseInt(sensors.getText())>2 || Integer.parseInt(sensors.getText())<1) {
            errorMessage += "No valid numbers of temp sensors!\n";
        }

        if (modules.getText() == null || modules.getText().length() == 0 || Integer.parseInt(modules.getText())>6 || Integer.parseInt(modules.getText())<1) {
            errorMessage += "No valid modules number!\n";
        }

        if (sample.getText() == null || sample.getText().length() == 0){
            errorMessage += "No valid sample time!\n";
        }
        if (labelSelectedDirectory.getText() == null || labelSelectedDirectory.getText() == "No Directory selected" || labelSelectedDirectory.getText().length() == 0){
            errorMessage += "No valid output file path!\n";
        }
        if((choiceBox.getValue() == null) || (choiceBox.getValue() != ("Current measurements (yes)\n") && choiceBox.getValue() != ("Current measurements (no)\n"))) {
            errorMessage += "No valid choise!\n";
        }
        if(selectedFile == null) {
            errorMessage += "No valid source!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(mainApp.getInputStage());
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    @FXML
    private void directoryChooser() {
        labelSelectedDirectory.setText("Select Output file path");

        dir.setOnAction(event -> {
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory =
                    directoryChooser.showDialog(mainApp.getInputStage());

            if(selectedDirectory == null){
                labelSelectedDirectory.setText("No Directory selected");
            }else{
                labelSelectedDirectory.setText(selectedDirectory.getAbsolutePath());
            }
        });

    }

    @FXML
    private void fileChooser() {

        csv.setText("Select CSV file path");
        csv.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            selectedFile = fileChooser.showOpenDialog(mainApp.getInputStage());

            if (selectedFile == null) {
                System.out.println("No File selected");

            }
            else {
                System.out.println("File selected");
                System.out.println(selectedFile.toPath());
            }
        });
    }


}