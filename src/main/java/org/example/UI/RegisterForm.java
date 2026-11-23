package org.example.UI;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.Objects.UIObjects.StyledTextField;
import org.example.backend.signup;

public class RegisterForm extends VBox {

    public RegisterForm(boolean staff, Runnable onSuccess) {
        super(12);
        setPadding(new Insets(20));
        setMaxWidth(450);

        ComboBox<String> role = new ComboBox<>();
        role.getItems().add("Patient");
        if (staff) role.getItems().add("Doctor");

        StyledTextField name = new StyledTextField("John Smith");
        StyledTextField email = new StyledTextField("john@example.com");
        StyledTextField password = new StyledTextField("");
        StyledTextField phone = new StyledTextField("4165551234");
        StyledTextField x1 = new StyledTextField("");
        StyledTextField x2 = new StyledTextField("");
        Label l1 = new Label();
        Label l2 = new Label();
        Label msg = new Label();

        GridPane g = new GridPane();
        g.setHgap(10);
        g.setVgap(10);
        g.addRow(0, new Label("Role"), role);
        g.addRow(1, new Label("Name"), name);
        g.addRow(2, new Label("Email"), email);
        g.addRow(3, new Label("Password"), password);
        g.addRow(4, new Label("Phone"), phone);
        g.addRow(5, l1, x1);
        g.addRow(6, l2, x2);

        role.valueProperty().addListener((o, a, b) -> {
            boolean doc = "Doctor".equals(b);
            l1.setText(doc ? "Field" : "Illness");
            l2.setText(doc ? "Emergency phone" : "Symptoms (optional)");
            x1.clear();
            x2.clear();
        });
        role.setValue("Patient");

        Button submit = new Button("Create account");
        submit.setOnAction(e -> {
            String r = "Doctor".equals(role.getValue()) ? signup.signupDoctor(name.getText(), email.getText(), password.getText(), phone.getText(), x1.getText(), x2.getText()) : signup.signupPatient(name.getText(), email.getText(), password.getText(), phone.getText(), x1.getText(), x2.getText());
            if (r.equals("success")) {
                msg.setText("Registered");
                name.clear(); email.clear(); password.clear(); phone.clear(); x1.clear(); x2.clear();
                onSuccess.run();
            } else {
                msg.setText(r);
            }
        });

        getChildren().addAll(g, msg, submit);
    }
}