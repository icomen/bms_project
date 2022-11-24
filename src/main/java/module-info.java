module it.unicas.bms_project {
    requires javafx.controls;
    requires javafx.fxml;


    opens it.unicas.bms_project to javafx.fxml;
    exports it.unicas.bms_project;
}