module com.example.demo4 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.base;


    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires java.persistence;
    requires java.mail;

    opens Controlleur to javafx.fxml;
    opens Entity to javafx.base, javafx.fxml, javafx.graphics;

    exports Controlleur;
    exports com.example.demo4;

}