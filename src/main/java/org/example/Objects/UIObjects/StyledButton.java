package org.example.Objects.UIObjects;
import javafx.scene.control.Button;

public class StyledButton extends Button {
    private static final String idle = "-fx-background-color: #2563eb; -fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 8 18;";
    private static final String hover = "-fx-background-color: #1d4ed8; -fx-text-fill: white; -fx-background-radius: 6; -fx-padding: 8 18;";

    public StyledButton(String text){
        super(text);
        setStyle(idle);
        setOnMouseEntered(e->setStyle(hover));
        setOnMouseExited(e->setStyle(idle));
    }
}
