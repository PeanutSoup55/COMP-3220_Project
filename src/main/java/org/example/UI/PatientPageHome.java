package org.example.UI;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class PatientPageHome extends BorderPane {

    public PatientPageHome(Stage primaryStage){
        BorderPane page = makePage(primaryStage);
        this.setCenter(page);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    public BorderPane makePage(Stage primaryStage){
        BorderPane pane = new BorderPane();
        Text text = new Text("patient page");
        pane.setCenter(text);
        return pane;
    }

    /*TODO
       write functions to handle:
        lists of appointments,
        taking medications,
        account overview
        checking out doctors in area
     */
}
