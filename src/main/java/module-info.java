module it.unicas.bms_project
{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;
    requires com.jfoenix;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.hivemq.client.mqtt;
    requires eu.hansolo.medusa;
    requires com.opencsv;
    requires org.apache.commons.collections4;


    opens it.unicas.bms_project to javafx.fxml;
    exports it.unicas.bms_project;
}