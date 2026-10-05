package org.example.UI;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Objects.UIObjects.StyledButton;
import org.example.Objects.UIObjects.StyledTextField;
import org.example.backend.login;

public class LoginPage extends BorderPane {

    public LoginPage(Stage primaryStage){
        VBox loginForm = createLoginForm(primaryStage);
        this.setCenter(loginForm);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    private VBox createLoginForm(Stage primaryStage){
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
        StyledTextField emailField = new StyledTextField("john@example.com");
        StyledTextField passwordField = new StyledTextField("12bucklemyshoe");
        text.getChildren().addAll(email, password);
        input.getChildren().addAll(emailField, passwordField);
        HBox fields = new HBox();
        fields.getChildren().addAll(text, input);

        VBox buttons = new VBox();
        StyledButton button = new StyledButton("Login");
        StyledButton button2 = new StyledButton("Sign Up");

        button.setOnMouseClicked(e -> {
            String emailText = emailField.getText();
            String passwordText = passwordField.getText();
            String table = login.login(emailText, passwordText);
            if (table.equals("receptionist")) {
                primaryStage.setScene(new Scene(new ReceptionistPageHome(primaryStage), 1000, 700));
            } else if (table.equals("doctor")) {
                primaryStage.setScene(new Scene(new DoctorPageHome(primaryStage), 1000, 700));
            } else if (table.equals("patient")) {
                primaryStage.setScene(new Scene(new PatientPageHome(primaryStage), 1000, 700));
            } else {
                System.out.println("Invalid email or password!");
            }
        });

        buttons.getChildren().addAll(button, button2);
        form.getChildren().addAll(title, fields, buttons);
        return form;
    }


}
