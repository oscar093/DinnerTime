package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import viewControllers.ClientViewController;
import viewControllers.MyKitchenController;
import viewControllers.SearchViewController;

/**
 * Start clients gui and handles the scenes for different views.
 *
 * @author David
 */
public class Main extends Application {
	private static Stage primaryStage;
	private static BorderPane mainLayout;
	private static AnchorPane testLayout;
//	private static Client client;

	/** 
	 * This methos starts the client gui.
	 * 
	 * @author David
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/** 
	 * Starts login view.
	 * 
	 * @author David
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		showLoginView();
	}
	
	/** 
	 * Show the login view.
	 * 
	 * @author David
	 * @throws IOException
	 */
	public static void showLoginView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../fmxlView/LoginView.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout, 540, 400);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	/** 
	 * show the register view.
	 * 
	 * @author David
	 * @throws IOException
	 */
	public static void showRegisterView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../fmxlView/RegisterView.fxml"));
		mainLayout = loader.load();
		Scene scene = new Scene(mainLayout, 540, 400);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	/** 
	 * show the client view.
	 * 
	 * @author David
	 * @param client
	 * @throws IOException
	 */
	public static void showClientView(Client client) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../fmxlView/ClientView.fxml"));
		mainLayout = loader.load();
		ClientViewController cvc = loader.getController();
		cvc.setClient(client);
		cvc.addToTree();
		Scene scene = new Scene(mainLayout, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	
	/** 
	 * Show myKitshen view.
	 * 
	 * @author David
	 * @param client
	 * @throws IOException
	 */
	public static void showMyKitchenView(Client client) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../fmxlView/MyKitchenView.fxml"));
		mainLayout = loader.load();
		MyKitchenController mkc = loader.getController();
		mkc.setClient(client);
		Scene scene = new Scene(mainLayout, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	
	/** 
	 * show search view.
	 * 
	 * @author David
	 * @param client
	 * @throws IOException
	 */
	public static void showSearchView(Client client) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../fmxlView/SearchView.fxml"));
		mainLayout = loader.load();
		SearchViewController svc = loader.getController();
		svc.setClient(client);
		Scene scene = new Scene(mainLayout, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
}