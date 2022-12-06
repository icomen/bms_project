package it.unicas.bms_project;


import com.jfoenix.controls.JFXToggleButton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;


/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 *
 * @author Enrico Castrechini
 * @author Micaela Rivas
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;
    @FXML
    public JFXToggleButton dm;


    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }




    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        if (dm.isSelected()) {
            alert.getDialogPane().getStylesheets().add(getClass().getResource("DarkTheme.css").toString());
        }
        alert.setTitle("BMS App");
        alert.setHeaderText("About");

        alert.setContentText("Author: Enrico Castrechini & Micaela Rivas\nWebsite: http://www.unicas.it");

        alert.showAndWait();
    }


    /**
     * Closes the application.
     */

    @FXML
    private void handleExit() {
        mainApp.handleExit();

    }

    @FXML
    private void showBmsOverview() {
        mainApp.showBmsOverview();
    }

    @FXML
    private void showSourceView() {
        mainApp.showSourceView();
    }

    @FXML
    public void setDarkMode() {
        Boolean isSelected;
        isSelected = dm.isSelected();
            if (isSelected) {
                dm.getScene().getRoot().getStylesheets().add(getClass().getResource("DarkTheme.css").toString());
                mainApp.BMScontroller.fireSmokeTile.setBackgroundColor(Color.rgb(0,0,0));
                mainApp.BMScontroller.fireSmokeTile.setValueColor(Color.rgb(255, 255, 255));
                mainApp.BMScontroller.fireSmokeTile.setUnitColor(Color.rgb(255, 255, 255));
                mainApp.BMScontroller.fireSmokeTile.setTitleColor(Color.rgb(255, 255, 255));

                mainApp.BMScontroller.whiteImage.setOpacity(0);
                mainApp.BMScontroller.blackImage.setOpacity(1);
                mainApp.BMScontroller.batteryGauge.setForegroundBaseColor(Color.rgb(255, 255, 255));
                mainApp.BMScontroller.batteryGauge.setBarBackgroundColor(Color.rgb(255, 255, 255));

                mainApp.BMScontroller.statusTile.setBackgroundColor(Color.rgb(0,0,0));
                mainApp.BMScontroller.statusTile.setTitleColor(Color.rgb(255, 255, 255));
                mainApp.BMScontroller.statusTile.setForegroundColor(Color.rgb(255, 255, 255));
            }
            else {
                dm.getScene().getRoot().getStylesheets().remove(getClass().getResource("DarkTheme.css").toString());
                mainApp.BMScontroller.fireSmokeTile.setBackgroundColor(Color.rgb(255,255,255));
                mainApp.BMScontroller.fireSmokeTile.setValueColor(Color.rgb(0, 0, 0));
                mainApp.BMScontroller.fireSmokeTile.setUnitColor(Color.rgb(0, 0, 0));
                mainApp.BMScontroller.fireSmokeTile.setTitleColor(Color.rgb(0,0,0));

                mainApp.BMScontroller.whiteImage.setOpacity(1);
                mainApp.BMScontroller.blackImage.setOpacity(0);
                mainApp.BMScontroller.batteryGauge.setForegroundBaseColor(Color.rgb(0, 0, 0));
                mainApp.BMScontroller.batteryGauge.setBarBackgroundColor(Color.rgb(0, 0, 0));

                mainApp.BMScontroller.statusTile.setBackgroundColor(Color.rgb(255,255,255));
                mainApp.BMScontroller.statusTile.setTitleColor(Color.rgb(0, 0, 0));
                mainApp.BMScontroller.statusTile.setForegroundColor(Color.rgb(0, 0, 0));
            }
    }

}