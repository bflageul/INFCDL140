module com.cesi.bloc.javafx.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.cesi.bloc.javafx.javafx to javafx.fxml;
    exports com.cesi.bloc.javafx.javafx;
}