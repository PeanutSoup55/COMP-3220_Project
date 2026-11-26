package org.example.Objects.UIObjects;

import javafx.scene.Node;
import javafx.scene.control.Tab;

import java.util.function.Supplier;

public class UiUtil {

    public static Tab tab(String title, Supplier<Node> content) {
        Tab t = new Tab(title, content.get());
        t.setClosable(false);
        t.setOnSelectionChanged(e -> {
            if (t.isSelected()) t.setContent(content.get());
        });
        return t;
    }
}
