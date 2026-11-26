package org.example.UI;

import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Objects.Patient;
import org.example.Objects.UIObjects.GlobalPageStandard;
import org.example.Objects.UIObjects.UiUtil;
import org.example.backend.fetchData;

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
}
