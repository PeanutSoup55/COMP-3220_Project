package org.example.UI;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Objects.UIObjects.GlobalPageStandard;
import org.example.Objects.UIObjects.UiUtil;
import org.example.backend.Session;
import org.example.backend.appointments;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DoctorPageHome extends GlobalPageStandard {

    private final int did = Session.id;

    public DoctorPageHome(Stage primaryStage) {
        super(primaryStage, "Doctor");
        TabPane tabs = new TabPane(
                UiUtil.tab("Today", () -> list(true)),
                UiUtil.tab("Week", this::schedule),
                UiUtil.tab("Upcoming", () -> list(false)));
        pageContent(new VBox(tabs));
    }

    private Node list(boolean todayOnly) {
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));
        String now = appointments.now();
        String today = now.substring(0, 10);
        for (String[] a : appointments.forDoctor(did)) {
            if (todayOnly ? !a[0].startsWith(today) : a[0].compareTo(now) <= 0) continue;
            box.getChildren().add(new Label(a[0] + "  " + a[1]
                    + "\n    phone: " + a[2] + "   email: " + a[3]
                    + "\n    illness: " + a[4] + "   symptoms: " + a[5]));
        }
        if (box.getChildren().isEmpty()) box.getChildren().add(new Label("Nothing scheduled"));
        return box;
    }

    private Node schedule() {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10));

        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] times = {"09:00", "10:00", "11:00", "12:00", "13:00", "14:00"};
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        Map<String, String> booked = new HashMap<>();
        for (String[] a : appointments.forDoctor(did)) booked.put(a[0], a[1]);
        Set<String> open = new HashSet<>();
        for (String[] s : appointments.allSlots(did)) open.add(s[0]);

        for (int col = 0; col < days.length; col++) {
            Label dayLabel = new Label(days[col] + "\n" + monday.plusDays(col));
            dayLabel.setStyle("-fx-font-weight: bold; -fx-padding: 10;");
            GridPane.setHalignment(dayLabel, HPos.CENTER);
            grid.add(dayLabel, col + 1, 0);
        }
        for (int row = 0; row < times.length; row++) {
            Label timeLabel = new Label(times[row]);
            timeLabel.setStyle("-fx-padding: 15;");
            GridPane.setHalignment(timeLabel, HPos.CENTER);
            grid.add(timeLabel, 0, row + 1);
            for (int col = 0; col < days.length; col++) {
                String key = monday.plusDays(col) + " " + times[row];
                String text = booked.containsKey(key) ? booked.get(key) : open.contains(key) ? "open" : "";
                Label cell = new Label(text);
                cell.setPadding(new Insets(15));
                GridPane.setHalignment(cell, HPos.CENTER);
                grid.add(cell, col + 1, row + 1);
            }
        }
        return grid;
    }
}