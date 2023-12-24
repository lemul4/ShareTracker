package com.example.sharetracker;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Класс ShareTrackerApp представляет точку входа для приложения JavaFX "Share Tracker".
 * Он инициализирует и запускает пользовательский интерфейс для отслеживания ценных бумаг.
 */
public class ShareTrackerApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ShareTrackerApp.class.getResource("share-tracker.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 443, 500);
        stage.setTitle("Share Tracker");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
