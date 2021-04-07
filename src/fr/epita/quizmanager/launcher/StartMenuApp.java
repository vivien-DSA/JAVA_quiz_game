package fr.epita.quizmanager.launcher;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StartMenuApp extends Application {

	@Override
	public void start(Stage firstStage) throws Exception {
		// initialisation of the windows
		firstStage.setTitle("QUIZ MANAGER");
		firstStage.setWidth(500);
		firstStage.setHeight(300);
		firstStage.setResizable(false);
		
		// adding the scene
			// Title, choice Box user, credentials text boxes and enter button
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));
		
				// title
		Text scenetitle = new Text("WELCOME TO QUIZ MANAGER");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 3, 1);
		
				// user and choice box
		Label user = new Label("user:");
		grid.add(user, 0, 1);
				
		ChoiceBox<String> cb = new ChoiceBox<>(FXCollections.observableArrayList(
			    "Admin", "Teacher", "Student")
			);
		cb.setValue("Teacher");
		cb.setTooltip(new Tooltip("Select an option"));
		grid.add(cb, 1, 1);
		
				// credentials
		Label userName = new Label("User Name:");
		grid.add(userName, 0, 2);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 2);

		Label pw = new Label("Password:");
		grid.add(pw, 0, 3);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 3);
		
		
				// Enter button
		Button btn = new Button("Sign in");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);
		
		// showing the windows
		Scene scene = new Scene(grid, 300, 275);
		firstStage.setScene(scene);
		firstStage.show();
	}
	public static void main(String[] args) {
		Application.launch(args);
		}
}

