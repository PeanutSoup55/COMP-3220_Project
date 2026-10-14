package org.example.Objects.UIObjects;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GlobalPageStandard extends BorderPane {
    protected VBox contentArea;

    public GlobalPageStandard(Stage primaryStage, String role){
        contentArea = new VBox(15);
        contentArea.setPadding(new Insets(15));
        contentArea.setStyle("-fx-background-color: #f8fafc;");
        this.setPrefSize(1000, 700);
        setTop(createHeader(role));
        setLeft(createSidebar(role));
        setCenter(contentArea);
    }

    private HBox createHeader(String role){
        HBox header = new HBox();
        header.setStyle("-fx-background-color: #0f172a; -fx-padding: 18px 20px;");
        Text a = new Text(role + " Dashboard Portal");
        a.setStyle("-fx-fill: #f1f5f9; -fx-font-size: 18px; -fx-font-weight: 600;");
        header.getChildren().add(a);
        return header;
    }

    private VBox createSidebar(String role){
        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-background-color: #1e293b; -fx-padding: 15px;");
        sidebar.setPrefWidth(200);

        Text menuTitle = new Text("Navigation");
        menuTitle.setStyle("-fx-fill: #94a3b8; -fx-font-size: 12px;");

        StyledButton homeBtn = new StyledButton("Home");
        StyledButton profileBtn = new StyledButton("Profile");
        StyledButton logoutBtn = new StyledButton("Logout");

        homeBtn.setMaxWidth(Double.MAX_VALUE);
        profileBtn.setMaxWidth(Double.MAX_VALUE);
        logoutBtn.setMaxWidth(Double.MAX_VALUE);

        sidebar.getChildren().addAll(menuTitle, homeBtn, profileBtn, logoutBtn);
        return sidebar;
    }

    protected void pageContent(Node content){
        contentArea.getChildren().setAll(content);
    }
}