package org.example.UI;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Objects.Patient;
import org.example.Objects.UIObjects.GlobalPageStandard;
import org.example.Objects.UIObjects.UiUtil;
import org.example.backend.appointments;
import org.example.backend.fetchData;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReceptionistPageHome extends GlobalPageStandard {
    public ReceptionistPageHome(Stage primaryStage) {
        super(primaryStage, "Receptionist");
        TabPane tabs = new TabPane(
                UiUtil.tab("Patients", this::PatientList),
                UiUtil.tab("Register", () -> new RegisterForm(true, () -> {})),
                UiUtil.tab("Availability", this::availability),
                UiUtil.tab("Appointments", this::allAppointments));
        pageContent(new VBox(tabs));
    }

    public VBox PatientList(){
        VBox patients = new VBox(15);
        Text a = new Text("Patient List");
        patients.getChildren().add(a);

        for (Patient patient : fetchData.GetPatientData()){
            Text patientName = new Text(patient.getName() + " (email: " + patient.getEmail() + ")");
            patients.getChildren().add(patientName);
        }

        return patients;
    }

    private Node availability(){
        VBox box = new VBox(10);
        box.setPadding(new Insets(15));

        Map<String, Integer> ids = new LinkedHashMap<>();
        for (String[] d : appointments.doctors(null)) {
            ids.put(d[1] + " (" + d[2] + ")", Integer.parseInt(d[0]));
        }
        ComboBox<String> doctor = new ComboBox<>();
        doctor.getItems().addAll(ids.keySet());

        DatePicker date = new DatePicker(LocalDate.now());
        ComboBox<String> time = new ComboBox<>(FXCollections.observableArrayList(
                "09:00", "10:00", "11:00", "12:00", "13:00", "14:00"));
        time.setValue("09:00");

        VBox slots = new VBox(6);
        Button add = new Button("Add slot");
        doctor.setOnAction(e -> {
            if (doctor.getValue() != null) loadSlots(ids.get(doctor.getValue()), slots);
        });
        add.setOnAction(e -> {
            if (doctor.getValue() == null || date.getValue() == null || time.getValue() == null) return;
            int did = ids.get(doctor.getValue());
            appointments.addSlot(did, date.getValue(), time.getValue());
            loadSlots(did, slots);
        });

        box.getChildren().addAll(new HBox(10, new Label("Doctor"), doctor),
                new HBox(10, date, time, add), slots);
        return box;
    }

    private void loadSlots(int did, VBox slots) {
        slots.getChildren().clear();
        for (String[] s : appointments.allSlots(did)) {
            Button rm = new Button("Remove");
            rm.setOnAction(e -> {
                appointments.removeSlot(did, s[0]);
                loadSlots(did, slots);
            });
            slots.getChildren().add(new HBox(10, new Label(s[0] + (s[1].equals("1") ? "  (booked)" : "")), rm));
        }
    }

    private Node allAppointments() {
        VBox box = new VBox(8);
        box.setPadding(new Insets(15));
        for (String[] a : appointments.all()) {
            Button cancel = new Button("Cancel");
            HBox row = new HBox(10, new Label(a[1] + "   patient: " + a[2] + "   doctor: " + a[3]), cancel);
            cancel.setOnAction(e -> {
                appointments.cancelAny(Integer.parseInt(a[0]));
                box.getChildren().remove(row);
            });
            box.getChildren().add(row);
        }
        if (box.getChildren().isEmpty()) box.getChildren().add(new Label("No appointments"));
        return box;
    }
}
