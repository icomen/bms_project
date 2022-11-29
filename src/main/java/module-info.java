module it.unicas.bms_project {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;
    requires com.jfoenix;


    opens it.unicas.bms_project to javafx.fxml;
    exports it.unicas.bms_project;
}