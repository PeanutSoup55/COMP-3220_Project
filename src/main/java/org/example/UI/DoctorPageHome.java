package org.example.UI;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Objects.UIObjects.GlobalPageStandard;

public class DoctorPageHome extends GlobalPageStandard {
    public DoctorPageHome(Stage primaryStage) {
        super(primaryStage, "Doctor");
        GridPane content = schedule();
        pageContent(content);
    }

    public VBox appointments(){
        VBox list = new VBox();
        list.setPadding(new Insets(15));

        //TODO: make appointment function in fetchData

        return list;
    }

    public GridPane schedule(){
        GridPane scheduleGrid = new GridPane();
        scheduleGrid.setGridLinesVisible(true); // Draws the grid lines for you automatically
        scheduleGrid.setPadding(new Insets(10));

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] times = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00"};

        for (int col = 0; col < days.length; col++) {
            Label dayLabel = new Label(days[col]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10;");
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            scheduleGrid.add(dayLabel, col + 1, 0);
        }

        for (int row = 0; row < times.length; row++) {
            Label timeLabel = new Label(times[row]);
            timeLabel.setStyle("-fx-padding: 15;");
            GridPane.setHalignment(timeLabel, HPos.CENTER);
            scheduleGrid.add(timeLabel, 0, row + 1);
        }
        return scheduleGrid;

    }
}
