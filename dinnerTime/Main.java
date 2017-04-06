package dinnerTime;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private Stage primaryStage;
	private BorderPane mainLayout;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("DinnerTime");
		showLoginView();
	}
	
	private void showLoginView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("LoginView.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout, 540, 400);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void showClientView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("ClientView.fxml"));
		BorderPane clientView = loader.load();
		
		Stage newStage = new Stage();
		Scene scene = new Scene(clientView);
		newStage.setScene(scene);
		newStage.show();
	}
}