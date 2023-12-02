module sgu.ltudm.songssingersproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;
    requires javafx.media;
    requires java.desktop;
    requires org.jsoup;
    requires org.json;

    opens sgu.ltudm.songssingersproject to javafx.fxml;
    exports sgu.ltudm.songssingersproject;
    exports sgu.ltudm.songssingersproject.controllers;
    opens sgu.ltudm.songssingersproject.controllers to javafx.fxml;
    exports sgu.ltudm.songssingersproject.models;
    opens sgu.ltudm.songssingersproject.models to javafx.fxml;
    exports sgu.ltudm.songssingersproject.views;
    opens sgu.ltudm.songssingersproject.views to javafx.fxml;
    exports sgu.ltudm.songssingersproject.encryptions;
    opens sgu.ltudm.songssingersproject.encryptions to javafx.fxml;
}