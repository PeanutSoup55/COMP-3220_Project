package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.UI.LoginPage;
import org.example.backend.tables;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        tables.createTables();
        LoginPage root = new LoginPage(primaryStage);
        Scene scene = new Scene(root, 900, 500);

        primaryStage.setTitle("test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
