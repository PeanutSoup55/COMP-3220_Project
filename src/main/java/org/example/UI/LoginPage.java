package org.example.UI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginPage extends BorderPane {

    public LoginPage(Stage primaryStage){
        VBox loginForm = createLoginForm();
        this.setCenter(loginForm);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.show();
        createLoginForm();
    }

    private VBox createLoginForm(){
        VBox form = new VBox();
        form.setPadding(new Insets(40, 40, 40, 40));
        form.setStyle("-fx-border-color: #d3d3d3; -fx-border-radius: 8; -fx-background-radius: 8;");
        form.setMaxWidth(400);
        form.setMaxHeight(600);
        Text title = new Text("Login");
        title.setStyle("-fx-font-size: 48px; -fx-font-weight: 700;");
        VBox text = new VBox();
        text.setPadding(new Insets(10, 10, 10, 10));
        VBox input = new VBox();
        input.setPadding(new Insets(5, 5, 5,5 ));
        Text email = new Text("Email: ");
        Text password = new Text("Password: ");

        email.setStyle("-fx-font-size: 20;");
        password.setStyle("-fx-font-size: 20;");
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        text.getChildren().addAll(email, password);
        input.getChildren().addAll(emailField, passwordField);
        HBox fields = new HBox();
        fields.getChildren().addAll(text, input);

        VBox buttons = new VBox();
        Button button = new Button("Login");
        Button button2 = new Button("Sign Up");
        buttons.getChildren().addAll(button, button2);
        form.getChildren().addAll(title, fields, buttons);
        return form;
    }
}
