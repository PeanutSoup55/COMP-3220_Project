package org.example.UI;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.Objects.Patient;
import org.example.backend.fetchData;

public class ReceptionistPageHome extends BorderPane {
    public ReceptionistPageHome(Stage primaryStage) {
        setLeft(PatientList());
    }

    public VBox PatientList(){
        VBox patients = new VBox();
        Text a = new Text("Patient List");
        patients.getChildren().add(a);

        for (Patient patient : fetchData.GetPatientData()){
            Text patientName = new Text(patient.getName());
            patients.getChildren().add(patientName);
        }

        return patients;
    }
}
