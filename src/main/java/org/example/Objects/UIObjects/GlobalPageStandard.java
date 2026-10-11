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
        this.setPrefSize(1000, 700);
        setTop(createHeader(role));
        setLeft(createSidebar(role));
        setCenter(contentArea);
    }

    private HBox createHeader(String role){
        HBox header = new HBox();
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #000000; -fx-padding: 15px;");
        Text a = new Text(role + " Dashboard Portal");
        a.setStyle("-fx-fill: white; -fx-font-size: 18px;");
        header.getChildren().add(a);
        return header;
    }

    private VBox createSidebar(String role){
        VBox sidebar = new VBox(10);
        sidebar.setStyle("-fx-background-color: #1F150C; -fx-padding: 15px;");
        sidebar.setPrefWidth(200);

        Text menuTitle = new Text("Navigation");
        menuTitle.setStyle("-fx-fill: #E1DCC9;");

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
