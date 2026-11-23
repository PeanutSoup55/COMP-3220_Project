package org.example.UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignupPage extends BorderPane {
    public SignupPage(Stage primaryStage) {
        Text title = new Text("Sign Up");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: 700;");

        Runnable toLogin = () -> primaryStage.setScene(new Scene(new LoginPage(primaryStage), 1000, 700));
        Button back = new Button("Back to login");
        back.setOnAction(e -> toLogin.run());

        VBox box = new VBox(15, title, new RegisterForm(false, toLogin), back);
        box.setAlignment(Pos.CENTER);
        this.setCenter(box);

        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.show();
    }
}