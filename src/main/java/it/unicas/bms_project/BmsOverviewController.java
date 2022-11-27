package it.unicas.bms_project;

// Reference to the main application.


import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class BmsOverviewController {

    private MainApp mainApp;


    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public BmsOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {

    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }

}
