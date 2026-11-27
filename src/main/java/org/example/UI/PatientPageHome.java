package org.example.UI;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Objects.UIObjects.GlobalPageStandard;
import org.example.Objects.UIObjects.UiUtil;
import org.example.backend.Session;
import org.example.backend.appointments;
import org.example.backend.profile;

public class PatientPageHome extends GlobalPageStandard {

    private final int pid = Session.id;

    public PatientPageHome(Stage primaryStage) {
        super(primaryStage, "Patient");
        TabPane tabs = new TabPane(
                UiUtil.tab("Book", this::booking),
                UiUtil.tab("My Appointments", this::myAppointments),
                UiUtil.tab("Profile", this::profileTab));
        this.setCenter(tabs);
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    private Node booking() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));

        ComboBox<String> field = new ComboBox<>();
        field.getItems().add("All");
        appointments.fields().forEach(r -> field.getItems().add(r[0]));
        field.setValue("All");

        VBox list = new VBox(8);
        Runnable load = () -> {
            list.getChildren().clear();
            String f = "All".equals(field.getValue()) ? null : field.getValue();
            for (String[] d : appointments.doctors(f)) {
                int did = Integer.parseInt(d[0]);
                ComboBox<String> slots = new ComboBox<>();
                appointments.openSlots(did).forEach(r -> slots.getItems().add(r[0]));
                slots.setPromptText(slots.getItems().isEmpty() ? "no slots" : "pick a slot");
                Button book = new Button("Book");
                book.setDisable(slots.getItems().isEmpty());
                Label status = new Label();
                book.setOnAction(e -> {
                    String chosen = slots.getValue();
                    if (chosen == null) return;
                    status.setText(appointments.book(pid, did, chosen) ? "Booked" : "Slot taken");
                    slots.getItems().remove(chosen);
                    slots.setValue(null);
                });
                list.getChildren().add(new HBox(10, new Label(d[1] + " - " + d[2]), slots, book, status));
            }
        };
        field.setOnAction(e -> load.run());
        load.run();

        box.getChildren().addAll(new HBox(10, new Label("Specialty"), field), list);
        return box;
    }

    private Node myAppointments() {
        VBox box = new VBox(8);
        box.setPadding(new Insets(15));
        String now = appointments.now();
        for (String[] a : appointments.forPatient(pid)) {
            boolean upcoming = a[1].compareTo(now) > 0;
            HBox row = new HBox(10, new Label((upcoming ? "Upcoming  " : "Past  ") + a[1] + "  " + a[2] + " (" + a[3] + ")  " + a[4]));
            if (upcoming) {
                Button cancel = new Button("Cancel");
                cancel.setOnAction(e -> {
                    appointments.cancelOwn(Integer.parseInt(a[0]), pid);
                    box.getChildren().remove(row);
                });
                row.getChildren().add(cancel);
            }
            box.getChildren().add(row);
        }
        if (box.getChildren().isEmpty()) box.getChildren().add(new Label("No appointments"));
        return box;
    }

    private Node profileTab() {
        String[] p = profile.get(pid);
        TextField name = new TextField(p[0]);
        TextField phone = new TextField(p[1]);
        TextField illness = new TextField(p[2]);
        TextField symptoms = new TextField(p[3]);
        Label msg = new Label();
        Button save = new Button("Save");
        save.setOnAction(e -> msg.setText(profile.update(pid, name.getText(), phone.getText(), illness.getText(), symptoms.getText()) ? "Saved" : "Name, phone and illness are required"));
        GridPane g = new GridPane();
        g.setPadding(new Insets(15));
        g.setHgap(10);
        g.setVgap(10);
        g.addRow(0, new Label("Name"), name);
        g.addRow(1, new Label("Phone"), phone);
        g.addRow(2, new Label("Illness"), illness);
        g.addRow(3, new Label("Symptoms"), symptoms);
        g.addRow(4, save, msg);
        return g;
    }
}