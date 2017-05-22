package viewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import client.Main;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import server.DatabaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;

/**
 * Controller class for myKitchen view.
 * 
 * @Author Jonathan, David
 */
public class MyKitchenController implements Initializable {
	@FXML
	private static Stage primaryStage;
	@FXML
	private AnchorPane pane;
	private Client client;
	@FXML
	private Main main;
	@FXML
	private Button btnBack;
	@FXML
	private Label usernameLbl;
	@FXML
	private DatabaseController dc;
	@FXML
	private Label firstName;
	@FXML
	private Label infoLbl;
	@FXML
	private Label surNameLbl;
	@FXML
	private Label regionLbl;
	@FXML
	private Label countryLbl;
	@FXML
	private ComboBox<String> myRecipesCb;
	@FXML
	private ObservableList<String> myRecipes = FXCollections.observableArrayList();
	@FXML
	private TextArea taRecipe;

	/**
	 * The TextArea is only for showing recipes
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taRecipe.setEditable(false);
	}

	/**
	 * Method shows new recipe view.
	 * 
	 * @throws IOException
	 */
	@FXML
	private void newRecipe() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("../fmxlView/CreateRecipeView.fxml"));
		pane = loader.load();

		CreateRecipeController crc = loader.getController();
		crc.setClient(client);

		Stage addRecipeStage = new Stage();
		addRecipeStage.setTitle("Create Recipe!");
		addRecipeStage.initModality(Modality.WINDOW_MODAL);
		addRecipeStage.initOwner(primaryStage);
		addRecipeStage.setResizable(false);

		Scene scene = new Scene(pane);
		addRecipeStage.setScene(scene);
		addRecipeStage.showAndWait();
	}

	/**
	 * Method for setting the client's first name in MyKitchen
	 * 
	 * @author Jonathan
	 */
	@FXML
	public void setFirstName() {
		firstName.setText("First name: " + client.getFirstName());
	}
	
	/**
	 * Method for setting the client's username in MyKitchen
	 * 
	 * @author Jonathan
	 */
	@FXML
	public void setUsername() {
		infoLbl.setText("Info about " + client.getUsername());
	}
	
	/**
	 * Method for setting the client's surname 
	 * 
	 * @author Jonathan
	 */
	@FXML
	public void setSurName() {
		surNameLbl.setText("Surname: " + client.getSurName());
	}
	
	/**
	 * Method for setting the title of MyKitchen to the client's username
	 * 
	 * @author Jonathan
	 */
	@FXML
	public void setName() {
		usernameLbl.setText(client.getUsername() + "'s" + " Kitchen");
		usernameLbl.setFont(Font.font("Arial Black", 24.0));
		usernameLbl.setAlignment(Pos.CENTER);
	}
	
	/**
	 * Method for setting the client's region in MyKitchen
	 * 
	 * @author Jonathan
	 */
	@FXML
	public void setRegion() {
		regionLbl.setText("Region: " + client.getRegion());
	}
	
	/**
	 * Method for setting the client's country in MyKitchen
	 */
	@FXML
	public void setCountry() {
		countryLbl.setText("Country: " + client.getCountry());

	}

	/**
	 * Method for setting all the client's recipes in MyKitchen
	 * 
	 * @author Jonathan
	 */
	@FXML
	public void setMyRecipes() {
		String[] recipes = client.getRecipes();
		for (int i = 0; i < recipes.length; i++) {
			myRecipes.add(recipes[i]);
		}
		myRecipesCb.setItems(myRecipes);
	}
	
	/**
	 * Method for sending the chosen recipe's title to the server
	 * 
	 * @author Jonathan
	 */
	@FXML
	private void recipeChoice() {
		String recipeName = (String) myRecipesCb.getValue();
		client.sendToServer("reccipe," + client.getUsername() + "," + recipeName);
	}

	/**
	 * Method for showing the chosen recipe in MyKitchen
	 * 
	 * @athor Jonathan, Olof
	 * @param recipe
	 */
	public void showRecipe(String[] recipe) {
		String list = "";
		for (int j = 0; j < recipe.length - 1; j++) {
			String[] split = recipe[j].split("_");
			list += split[0].toUpperCase() + ":\n";
			for (int i = 1; i < split.length; i++) {
				list += split[i] + "\n";
			}
		}
		taRecipe.setText(list);
	}
	
	/**
	 * Method for going back to ClientView
	 * @author Jonathan
	 */
	@FXML
	private void back() {
		try {
			main.showClientView(client);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set which client to be used.
	 * 
	 * @David
	 * @param client
	 */

	public void setClient(Client client) {
		this.client = client;
		client.setMyKitchenController(this);

	}
}