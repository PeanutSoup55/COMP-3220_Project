package org.example.Objects.UIObjects;

import javafx.scene.control.TextField;

public class StyledTextField extends TextField {
    private static final String idle = "-fx-background-color: white; -fx-border-color: #d1d5db; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;";
    private static final String entered = "-fx-background-color: white; -fx-border-color: #2563eb; -fx-border-radius: 6; -fx-background-radius: 6; -fx-padding: 8;";
    public StyledTextField(String prompt){
        setPromptText(prompt);
        setStyle(idle);
        focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused) {
                setStyle(entered);
            } else {
                setStyle(idle);
            }
        });
    }
}
