module bob.lobcorptm {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires eu.hansolo.tilesfx;
    requires java.management;

    opens bob.lobcorptm to javafx.fxml;
    exports bob.lobcorptm;
}