import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	static ArrayList<Student> students = new ArrayList<Student>();
	static String searchPerson = "";
	static double totalCount = 0;
	static boolean isNumber;

	public static void main(String[] args) throws IOException {

		File in = new File("list.txt");
		Scanner scan = new Scanner(in);

		String word;

		while(scan.hasNextLine()) {
			word = scan.nextLine();
			addStudent(word);
			totalCount++;
		}
		scan.close();
		launch(args);
	}

	public static void addStudent(String name) {
		
		boolean isPresList = false;
		String[] splitNames = name.split(",");
		String ln = splitNames[0];
		String fn = splitNames[1].trim();

		if(fn.contains("*")) {
			isPresList = true;
			int starIndex = fn.indexOf("*");
			fn = fn.substring(0, starIndex - 1);
		}
		students.add(new Student(fn, ln, isPresList));
	}

	public void start(Stage primaryStage) throws Exception {

		GridPane pane = new GridPane();

		Label intro = new Label("Enter a Name to Search:");
		TextField text = new TextField();

		Button searchButton = new Button("Search");
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {

            	retrieveText(text.getText());
                GridPane namePane = search(searchPerson);
                VBox secondaryLayout = new VBox();

                Label secondLabel = new Label("Name Results");

                ScrollPane scroll = new ScrollPane();
                scroll.setPrefHeight(200);

                scroll.setContent(namePane);

                secondaryLayout.getChildren().add(secondLabel);
                secondaryLayout.getChildren().add(scroll);

                secondaryLayout.setAlignment(Pos.TOP_CENTER);

                Scene secondScene = new Scene(secondaryLayout, 250, 250);

                Stage secondStage = new Stage();
                secondStage.setScene(secondScene);

                //Set position of second window, related to primary window.
                secondStage.setX(primaryStage.getX() + 50);
                secondStage.setY(primaryStage.getY());

                secondStage.show();
            }
        });

		pane.add(intro, 0, 0, 2, 1);
		pane.add(text, 0, 1);
		pane.add(searchButton, 1, 1);

		//Formatting
		pane.setPadding(new Insets(25, 30, 30, 30));
		Scene scene = new Scene(pane, 300, 100);
		primaryStage.setTitle("Dean's List");
		primaryStage.setResizable(false);

		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private GridPane search(String term) {
		GridPane retPane = new GridPane();

		int matchCount = 0, errCount = 0;
		String[] splits = term.split(" ");
		String termFirstName = "", termLastName = "";

		termFirstName = splits[0];
		if(splits.length > 1)
			termLastName = splits[1];

		for(int i = 0; i < totalCount; i++) {
			
			if(isNumber) {
				if(errCount < 1) {
					retPane.add(new Label("No Numbers Allowed"), 0, 0);
					errCount++;
				}
				break;
			}
			
			if(splits.length > 1) {
				
				if((students.get(i).getFirstName().contains(termFirstName) || students.get(i).getLastName().contains(termFirstName))
					&& (students.get(i).getFirstName().contains(termLastName) || students.get(i).getLastName().contains(termLastName))) {
					
					if(students.get(i).getOnPresList()) {
						retPane.add(new Label(students.get(i).getLastName() + ", " + students.get(i).getFirstName() + " -- President's List"), 0, matchCount);
						matchCount++;
					} else {
						retPane.add(new Label(students.get(i).getLastName() + ", " + students.get(i).getFirstName()), 0, matchCount);
						matchCount++;
					}
				}
			} else {
				
				if((students.get(i).getFirstName().contains(termFirstName) || students.get(i).getLastName().contains(termFirstName))) {
					
					if(students.get(i).getOnPresList()) {
						retPane.add(new Label(students.get(i).getLastName() + ", " + students.get(i).getFirstName()), 0, matchCount);
						retPane.add(new Label(students.get(i).getLastName() + ", " + students.get(i).getFirstName()), 0, matchCount);
						retPane.add(new Label(students.get(i).getLastName() + ", " + students.get(i).getFirstName()), 0, matchCount);
						retPane.add(new Label(students.get(i).getLastName() + ", " + students.get(i).getFirstName()), 0, matchCount);
						matchCount++;
					} else {
						retPane.add(new Label(students.get(i).getLastName() + ", " + students.get(i).getFirstName()), 0, matchCount);
						matchCount++;
					}
				}
			}
		}
	return retPane;
	}

	private boolean retrieveText(String message){
		try {
			Integer.parseInt(message);
			isNumber = true;
			return false;
		} catch(NumberFormatException e) {
			isNumber = false;
			searchPerson = message;
			return true;
		}
	}
}
