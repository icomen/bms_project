module it.unicas.bms_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;
    requires com.jfoenix;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.hivemq.client.mqtt;
    requires eu.hansolo.medusa;
    requires com.opencsv;


    opens it.unicas.bms_project to javafx.fxml;
    exports it.unicas.bms_project;
}