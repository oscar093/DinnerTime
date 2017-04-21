package dinnerTime;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static AnchorPane testLayout;


	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		showLoginView();
	}
	
	public static void showLoginView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("LoginView.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout, 540, 400);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void showRegisterView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("RegisterView.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout, 540, 400);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	public static void showClientView(Client client) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("ClientView.fxml"));
		mainLayout = loader.load();
		ClientViewController cvc = loader.getController();
		cvc.setClient(client);
		Scene scene = new Scene(mainLayout, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	
	public static void showMyKitchenView() throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MyKitchenView.fxml"));
		MyKitchenController mkc = loader.getController();
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
}