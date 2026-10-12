package org.example.UI;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Objects.UIObjects.GlobalPageStandard;

public class DoctorPageHome extends GlobalPageStandard {
    public DoctorPageHome(Stage primaryStage) {
        super(primaryStage, "Doctor");
        VBox content = appointments();
        pageContent(content);
    }

    public VBox appointments(){
        VBox list = new VBox();
        list.setPadding(new Insets(15));

        //TODO: make appointment function in fetchData

        return list;
    }
}
