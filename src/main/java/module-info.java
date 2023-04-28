module CrumbTracker.main {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    exports crumbTracker to  javafx.graphics;
    exports crumbTracker.controller to  javafx.fxml;
    opens crumbTracker.controller to javafx.fxml;




}