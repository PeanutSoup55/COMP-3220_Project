package org.example.UI;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Objects.Patient;
import org.example.Objects.UIObjects.GlobalPageStandard;
import org.example.backend.fetchData;

public class ReceptionistPageHome extends GlobalPageStandard {
    public ReceptionistPageHome(Stage primaryStage) {
        super(primaryStage, "Receptionist");
        VBox content = PatientList();
        pageContent(content);
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
