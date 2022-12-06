package it.unicas.bms_project;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class InputStartViewController {

    private MainApp mainApp;

    private Stage dialogStage;
    @FXML
    private TextField cells;
    @FXML
    private TextField sensors;
    @FXML
    private TextField modules;
    @FXML
    private TextField sample;
    @FXML
    private TextField path;

    private boolean okClicked = false;

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void simpleExit() {
        mainApp.simpleExit();

    }

    @FXML
    private void setPrimaryStage() {
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
    private void handleOk() {
        if (isInputValid()) {
            //settings.setHost(hostField.getText());
            //settings.setUserName(usernameField.getText());
            //settings.setPwd(passwordField.getText());
            //settings.setSchema(schemaField.getText());
            try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("myfile.txt", true))))
            {
                out.println(cells.getText());
                out.println(sensors.getText());
                out.println(modules.getText());
                out.println(sample.getText());
                out.println(path.getText());
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

        if (cells.getText() == null || cells.getText().length() == 0) {
            errorMessage += "No valid cells number!\n";
        }
        if (sensors.getText() == null || sensors.getText().length() == 0) {
            errorMessage += "No valid numbers of temp sensors!\n";
        }

        if (modules.getText() == null || modules.getText().length() == 0) {
            errorMessage += "No valid modules number!\n";
        }
        if (sample.getText() == null || sample.getText().length() == 0){
            errorMessage += "No valid sample time!\n";
        }
        if (path.getText() == null || path.getText().length() == 0){
            errorMessage += "No valid output file path!\n";
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

}
