package org.example.UI;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class LoginPage extends BorderPane {

    public LoginPage(){
        createLoginForm();
    }

    private VBox createLoginForm(){
        VBox form = new VBox();
        HBox emailhbox = new HBox();
        HBox passwordhbox = new HBox();
        Text email = new Text("Email: ");
        Text password = new Text("Password: ");
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        emailhbox.getChildren().addAll(email, emailField);
        passwordhbox.getChildren().addAll(password, passwordField);
        form.getChildren().addAll(emailhbox, passwordhbox);
        return form;
    }
}
