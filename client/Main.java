package client;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import viewControllers.ClientViewController;
import viewControllers.MyKitchenController;
import viewControllers.SearchViewController;

/**
 * Start clients gui and handles the scenes for different views.
 *
 * @author David, Jonathan
 */
public class Main extends Application {
	private static Stage primaryStage;
	private static BorderPane mainLayout;

	/** 
	 * This method starts the client gui.
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
	 * Shows the login view.
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
	 * Shows the register view.
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
	 * shows the client view.
	 * 
	 * @author David, Jonathan
	 * @param client
	 * @throws IOException
	 */
	public static void showClientView(Client client) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../fmxlView/ClientView.fxml"));
		mainLayout = loader.load();
		ClientViewController cvc = loader.getController();
		cvc.setClient(client);
		client.sendToServer("username" + client.getUsername());
		client.sendToServer("surname" + client.getUsername());
		client.sendToServer("region" + client.getUsername());
		client.sendToServer("country" + client.getUsername());
		client.sendToServer("recipes" + client.getUsername());
		cvc.addToTree();
		Scene scene = new Scene(mainLayout, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	
	/** 
	 * Shows myKitchen view.
	 * 
	 * @author David, Jonathan
	 * @param client
	 * @throws IOException
	 */
	public static void showMyKitchenView(Client client) throws IOException{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../fmxlView/MyKitchenView.fxml"));
		mainLayout = loader.load();
		MyKitchenController mkc = loader.getController();
		mkc.setClient(client);
		mkc.setUsername();
		mkc.setName();
		mkc.setFirstName();
		mkc.setSurName();
		mkc.setRegion();
		mkc.setCountry();
		mkc.setMyRecipes();
		mkc.setClient(client);
		Scene scene = new Scene(mainLayout, 900, 600);
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);
		primaryStage.show();
	}
	
	/** 
	 * Shows search view.
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