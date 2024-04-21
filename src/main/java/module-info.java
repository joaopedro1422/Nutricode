module com.example.nutricode_ {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires de.jensd.fx.fontawesomefx.fontawesome;
    requires de.jensd.fx.fontawesomefx.commons;
    opens com.example.nutricode_ to javafx.fxml;
    exports com.example.nutricode_;
    requires javafx.graphics;
    requires org.controlsfx.controls;
    requires jasperreports;
    requires java.desktop;


}