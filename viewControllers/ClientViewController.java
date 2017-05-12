package viewControllers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JLabel;

import client.Client;
import client.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import module.Logout;
import module.Recipe;

/**
 * Controller class for client startscene, country explore.
 * 
 * @author Oscar
 */
public class ClientViewController implements Initializable {

	@FXML
	private Main main;
	@FXML
	private TreeView<String> treeview;
	@FXML
	private Button logout;
	@FXML
	private AnchorPane anchorpane;
	@FXML
	private ScrollPane scrollpane;
	private Client client;
	private String username;
	private Logout userLogout;
	private TreeItemListener til = new TreeItemListener();
	private BackgroundFill b;
	private TreeItem<String> root = new TreeItem<>();
	private TreeItem<String> africa;
	private TreeItem<String> asia;
	private TreeItem<String> europe;
	private TreeItem<String> middleEast;
	private TreeItem<String> northAmerica;
	private TreeItem<String> southAmerica;
	private ArrayList<TreeItem<String>> countryItemList = new ArrayList<TreeItem<String>>();
	private HashMap<TreeItem<String>, Integer> recipeKeyMap = new HashMap<TreeItem<String>, Integer>();
	private int recipeCount = -1;

	/**
	 * Initialize and show GUI for client.
	 * 
	 * @author Oscar, Olof, David
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		b = new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY);
		anchorpane.setBackground(new Background(b));
		anchorpane.setMaxWidth(535);
		scrollpane.setBackground(
				new Background((new BackgroundFill(Color.rgb(244, 244, 244), CornerRadii.EMPTY, Insets.EMPTY))));
	}

	public void addToTree() {
		africa = new TreeItem<String>("Africa");
		countryItemList.add(africa);
		addRegion("Africa");

		asia = new TreeItem<String>("Asia");
		countryItemList.add(asia);
		addRegion("Asia");

		europe = new TreeItem<String>("Europe");
		countryItemList.add(europe);
		addRegion("Europe");

		middleEast = new TreeItem<String>("Middle East");
		countryItemList.add(middleEast);
		addRegion("MiddleEast");

		northAmerica = new TreeItem<String>("North America");
		countryItemList.add(northAmerica);
		addRegion("NorthAmerica");

		southAmerica = new TreeItem<String>("South America");
		countryItemList.add(southAmerica);
		addRegion("SouthAmerica");

		root.getChildren().add(africa);

		root.getChildren().add(asia);

		root.getChildren().add(europe);

		root.getChildren().add(middleEast);

		root.getChildren().add(northAmerica);

		root.getChildren().add(southAmerica);

		treeview.getSelectionModel().selectedItemProperty().addListener(til);
		treeview.setRoot(root);
		treeview.setShowRoot(false);
	}

	/**
	 * author Olof
	 * 
	 * Add regions to TreeView.
	 * 
	 * @param region
	 */
	private synchronized void addRegion(String region) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/txtFiles/" + region + ".txt"));
			String strLine = br.readLine();
			while (strLine != null) {
				TreeItem<String> country = new TreeItem<String>(strLine);
				countryItemList.add(country);
				strLine = br.readLine();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * author Olof
	 * 
	 * Add countries to treeView.
	 * 
	 * @param regionString
	 * @param regionItem
	 */
	private void addCountries(String regionString, TreeItem<String> regionItem) {
		try {
			if (regionString.contains(" ")) {
				String[] noSpace = regionString.split(" ");
				regionString = noSpace[0] + noSpace[1];
			}
			BufferedReader br = new BufferedReader(new FileReader("src/txtFiles/" + regionString + ".txt"));
			String strLine = br.readLine();
			while (strLine != null) {
				for (int i = 0; i < countryItemList.size(); i++) {
					TreeItem<String> tempItem = countryItemList.get(i);
					if (tempItem.toString().contains(strLine)) {
						// for each country, a request to count the recipes from
						// the country is sent to the server/database
						client.sendToServer("getRecipeCount " + strLine);
						int recipeCount = getRecipeCount();
						tempItem.setValue(strLine + "(" + recipeCount + ")");
						regionItem.getChildren().add(tempItem);
					}
				}
				strLine = br.readLine();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * author Olof
	 * 
	 * returns the count of the recipe-count of each country. it waits until a
	 * new value is received by setRecipeCount()
	 * 
	 * @return : the number of recipes
	 */
	private synchronized int getRecipeCount() {
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return recipeCount;
	}

	/**
	 * author Olof
	 * 
	 * gets the recipe-count from the client and notifies all waiting methods
	 * 
	 * @param count
	 */
	public synchronized void setRecipeCount(int count) {
		this.recipeCount = count;
		notifyAll();
	}

	/**
	 * Set the Client of which communication is made.
	 * 
	 * @param client
	 */

	public void setClient(Client client) {
		this.client = client;
		client.setClientViewController(this);
	}

	/**
	 * Log out this user.
	 * 
	 * @throws IOException
	 */
	@FXML
	private void logout() throws IOException {
		userLogout = new Logout(username);
		client.sendToServer(userLogout);
		main.showLoginView();
	}

	/**
	 * Show myKitchen view.
	 * 
	 * @throws IOException
	 */
	@FXML
	private void myKitchen() throws IOException {
		main.showMyKitchenView(client);
	}

	/**
	 * show search view.
	 * 
	 * @throws IOException
	 */
	@FXML
	private void search() throws IOException {
		main.showSearchView(client);
	}

	/**
	 * Listens for operations in treeview from user and delegates to other
	 * methods. When a region is pressed, all the countries are added as
	 * children to the region.
	 * 
	 * @author Oscar, Olof
	 */
	private class TreeItemListener implements ChangeListener {
		@Override
		public void changed(ObservableValue arg0, Object arg1, Object arg2) {
			MultipleSelectionModel msm = treeview.getSelectionModel();
			TreeItem<String> selectedItem = (TreeItem<String>) msm.getSelectedItem();
			if (selectedItem.isLeaf() && !selectedItem.isExpanded() && countryItemList.contains(selectedItem)) {
				addCountries(selectedItem.getValue(), selectedItem);
				String[] countryName = selectedItem.getValue().split("\\(");
				String command = "getrecipebycountry " + countryName[0];
				client.sendToServer(command);
			} else if (selectedItem.isLeaf() && !countryItemList.contains(selectedItem)) {
				Recipe selectedRecipe = client.getRecipe(recipeKeyMap.get(selectedItem));
				if (selectedRecipe != null) {
					presentRecipe(selectedRecipe);
				} else {
					System.out.print("Something is wrong, the recipe does not exist.");
				}
			}
		}
	}

	/**
	 * Updated all country nodes with the latest recipes based on that
	 * particular country.
	 * 
	 * @author Oscar
	 * @param recipe
	 *            - is a Recipe array.
	 */
	public void updateCountryNode(Recipe[] recipe) {
		if (recipe.length > 0) {
			for (TreeItem<String> ti : countryItemList) {
				for (Recipe r : recipe) {
					if (ti.getValue().toLowerCase().startsWith(r.getCountry())) {
						TreeItem<String> title = new TreeItem<String>(
								r.getTitle().substring(0, 1).toUpperCase() + r.getTitle().substring(1));
						ti.getChildren().add(title);

						// test
						recipeKeyMap.put(title, r.getId());
					}
				}
			}
		}
	}

	/**
	 * Presents the recipes in the TextArea.
	 * 
	 * @author Oscar
	 * @param recipe
	 */
	public void presentRecipe(Recipe recipe) {
		anchorpane.getChildren().clear();
		double anchorPaneWidth = anchorpane.getWidth();
		Text title = new Text();
		title.setText(recipe.getTitle().substring(0, 1).toUpperCase() + recipe.getTitle().substring(1));
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
		Text text = new Text();
		text.setFont(Font.font("Verdana", 16));
		Text instructionTitle = new Text();
		JLabel jlb = new JLabel();
		instructionTitle.setText("Instruction");
		instructionTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 16));
		int picHeight = 0;

		if (recipe.getImg() != null) {
			ByteArrayInputStream in = new ByteArrayInputStream(recipe.getImg());
			Image img = new Image(in);
			ImageView recipeImage = new ImageView(img);
			recipeImage.setY(70);
			recipeImage.setX((anchorPaneWidth / 2) - 249.5);// 249.5 ska vara
															// halva recept
															// bilden.
			anchorpane.getChildren().add(recipeImage);
			picHeight = 312;

		}

		String recepyInfo = "Ursprung: " + recipe.getCountry().substring(0, 1).toUpperCase()
				+ recipe.getCountry().substring(1) + "\nFörfattare: " + recipe.getAuthor().substring(0, 1).toUpperCase()
				+ recipe.getAuthor().substring(1) + "\n\nTillagningstid: " + recipe.getTime() + " minuter\n";

		String[] ingridienser = recipe.getIngredients();
		for (String ingr : ingridienser) {
			recepyInfo += ingr + "\n";
		}

		text.setText(recepyInfo);
		Text instruction = new Text();
		String strInst = recipe.getInstruction();
		instruction.setText(recipe.getInstruction());

		instruction.setX(15);
		instruction.setY(70 + picHeight + 15 + text.getLayoutBounds().getHeight() + 35);

		title.setY(40);
		title.setX((anchorPaneWidth / 2) - (title.getLayoutBounds().getWidth() / 2));
		text.setY(70 + picHeight + 25);
		text.setX(15);

		instructionTitle.setY(70 + picHeight + 15 + text.getLayoutBounds().getHeight() + 15);
		instructionTitle.setX(15);
		instructionTitle.setBoundsType(TextBoundsType.LOGICAL);

		anchorpane.getChildren().add(title);
		anchorpane.getChildren().add(text);
		anchorpane.getChildren().add(instruction);
		anchorpane.getChildren().add(instructionTitle);
	}
}