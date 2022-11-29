package it.unicas.bms_project;


import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXToggleButton;





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
    private JFXToggleButton boton;


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
    public void setDarkMode() {
        Boolean isSelected;
        isSelected = boton.isSelected();
            if (isSelected) {
                boton.getScene().getRoot().getStylesheets().add(getClass().getResource("DarkTheme.css").toString());
            }
            else {
                boton.getScene().getRoot().getStylesheets().remove(getClass().getResource("DarkTheme.css").toString());
            }
    }

}