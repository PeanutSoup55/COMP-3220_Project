package org.example.UI;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignupPage extends BorderPane {
    public SignupPage(Stage primaryStage) {
        VBox signup = signup();
        this.setCenter(signup);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    public VBox signup(){
        VBox form = new VBox();
        return form;
    }

}
