package com.example.Application;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import java.util.EventListener;

public class CameraRotationApp extends Application {

    @Override
    public void start(Stage stage) throws Exception { //When starting the application all these things happen

      Parent fxmlfile = FXMLLoader.load(getClass().getResource("SquareViewer.fxml"));
      Scene scene = new Scene(fxmlfile);
      stage.setScene(scene);

      //equivalent of the default close operation, closes application when you press the red cross in the top left
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

      stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    } //This will launch the application
}


