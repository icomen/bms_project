package it.unicas.bms_project;

import javafx.fxml.FXML;

import java.io.IOException;

public class SessionManagerController {

    private MainApp mainApp;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void terminateSession() throws IOException {
        mainApp.terminateSession();
    }

}