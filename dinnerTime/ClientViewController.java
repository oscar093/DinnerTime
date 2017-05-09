package dinnerTime;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

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
	private Image kenyaImage = new Image(getClass().getResourceAsStream("/ke.png"));
	private Image moroccoImage = new Image(getClass().getResourceAsStream("/ma.png"));
	private Image chinaImage = new Image(getClass().getResourceAsStream("/cn.png"));
	private Image japanImage = new Image(getClass().getResourceAsStream("/jp.png"));
	private Image thailandImage = new Image(getClass().getResourceAsStream("/th.png"));
	private Image franceImage = new Image(getClass().getResourceAsStream("/fr.png"));
	private Image italyImage = new Image(getClass().getResourceAsStream("/it.png"));
	private Image swedenImage = new Image(getClass().getResourceAsStream("/se.png"));
	private Image iranImage = new Image(getClass().getResourceAsStream("/ir.png"));
	private Image lebanonImage = new Image(getClass().getResourceAsStream("/lb.png"));
	private Image mexicoImage = new Image(getClass().getResourceAsStream("/mx.png"));
	private Image usaImage = new Image(getClass().getResourceAsStream("/us.png"));
	private Image argentinaImage = new Image(getClass().getResourceAsStream("/ar.png"));
	private Image colombiaImage = new Image(getClass().getResourceAsStream("/co.png"));
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		africa = new TreeItem<String>("Africa");
		countryItemList.add(africa);
		addItems("africa");
		
		asia = new TreeItem<String>("Asia");
		countryItemList.add(asia);
		addItems("asia");
		
		europe = new TreeItem<String>("Europe");
		countryItemList.add(europe);
		addItems("europe");
		
		middleEast = new TreeItem<String>("Middle East");
		countryItemList.add(middleEast);
		addItems("middleEast");
		
		northAmerica = new TreeItem<String>("North America");
		countryItemList.add(northAmerica);
		addItems("northAmerica");
		
		southAmerica = new TreeItem<String>("South America");
		countryItemList.add(southAmerica);
		addItems("southAmerica");
		
		root.getChildren().add(africa);
		addChildren("africa",africa);
		
		root.getChildren().add(asia);
		addChildren("asia",asia);
		
		root.getChildren().add(europe);
		addChildren("europe",europe);
		
		root.getChildren().add(middleEast);
		addChildren("middleEast",middleEast);
		
		root.getChildren().add(northAmerica);
		addChildren("northAmerica",northAmerica);
		
		root.getChildren().add(southAmerica);
		addChildren("southAmerica",southAmerica);
		
		treeview.getSelectionModel().selectedItemProperty().addListener(til);
		treeview.setRoot(root);
		treeview.setShowRoot(false);
		
		b = new BackgroundFill(Color
	            .rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY);
		anchorpane.setBackground(new Background(b));
		anchorpane.setMaxWidth(535);
		scrollpane.setBackground(new Background((new BackgroundFill(Color
	            .rgb(244, 244, 244), CornerRadii.EMPTY, Insets.EMPTY))));
	}
	
	private void addItems(String region){
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/" + region + ".txt"));
			String strLine = br.readLine();
			while (strLine != null) {
				TreeItem<String> country = new TreeItem<String>(strLine);
				countryItemList.add(country);
				strLine = br.readLine();
			}
		} catch (IOException e) {
		}
	}
	
	private void addChildren(String regionString, TreeItem<String> regionItem){
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/" + regionString + ".txt"));
			String strLine = br.readLine();
			while (strLine != null) {
				for(int i = 0; i < countryItemList.size(); i++){
					TreeItem<String> tempItem = countryItemList.get(i);
					if(tempItem.toString().contains(strLine)){
						regionItem.getChildren().add(tempItem);
					}
				}
				strLine = br.readLine();
			}
		} catch (IOException e) {
		}
		
	}
	
	public void setClient(Client client) {
		this.client = client;
		client.setClientViewController(this);
	}
	
	@FXML
	private void logout() throws IOException {
		userLogout = new Logout(username);
		client.sendToServer(userLogout);
		main.showLoginView();
	}
	
	@FXML
	private void myKitchen() throws IOException{
		main.showMyKitchenView(client);
	}
	
	@FXML
	private void search() throws IOException{
		main.showSearchView(client);
	}
	
	/**
	 * Listens for operations in treeview.
	 * @author osc
	 *
	 */
	private class TreeItemListener implements ChangeListener{
		@Override
		public void changed(ObservableValue arg0, Object arg1, Object arg2) {
			MultipleSelectionModel msm = treeview.getSelectionModel();
			TreeItem<String> selectedItem = (TreeItem<String>) msm.getSelectedItem();
			if(selectedItem.isLeaf() && !selectedItem.isExpanded() && countryItemList.contains(selectedItem)){
				String command = "getrecipebycountry " + selectedItem.getValue();
				client.sendToServer(command);	
			}else if(selectedItem.isLeaf() && !countryItemList.contains(selectedItem)){
				Recipe selectedRecipe = client.getRecipe(recipeKeyMap.get(selectedItem));
				if(selectedRecipe != null){
					presentRecipe(selectedRecipe);	
				}else{
					System.out.print("Something is wrong, the recipe does not exist.");
				}
			}
		}
	}

	/**
	 * Updated all country nodes with the latest recipes based on that particular country.
	 * @param recipe - is a Recipe array.
	 */
	public void updateCountryNode(Recipe[] recipe) {
		if(recipe.length>0){
			for(TreeItem<String> ti : countryItemList){
				for (Recipe r : recipe){
					if(ti.getValue().toLowerCase().contentEquals(r.getCountry())){
						TreeItem<String> title = new TreeItem<String>(r.getTitle().substring(0,1).toUpperCase() 
																	+ r.getTitle().substring(1));
						ti.getChildren().add(title);
						
						//test
						recipeKeyMap.put(title, r.getId());
					}
				}
			}
		}
	}
	
	/**
	 * Presents the recipe in the textflow.
	 * @param recipe
	 */
	public void presentRecipe(Recipe recipe){
		anchorpane.getChildren().clear();
		Text title = new Text();
		title.setText(recipe.getTitle().substring(0,1).toUpperCase() + recipe.getTitle().substring(1));
		title.setFont(Font.font ("Verdana", 36));
		Text text = new Text();
		text.setFont(Font.font ("Verdana", 14));
		
		if(recipe.getImg() != null){
			ByteArrayInputStream in = new ByteArrayInputStream(recipe.getImg());
			Image img = new Image(in);
			ImageView vfoodPic = new ImageView(img);
			vfoodPic.setY(20);
			vfoodPic.setX(320);
			anchorpane.getChildren().add(vfoodPic);
		}
		
		String recepyInfo = "Ursprung: " + recipe.getCountry().substring(0,1).toUpperCase() + recipe.getCountry().substring(1)
							+ "\nFörfattare: " + recipe.getAuthor().substring(0,1).toUpperCase() + recipe.getAuthor().substring(1)
							+ "\n\nTillagningstid: " + recipe.getTime() + " minuter"
							+ "\n\nIngridienser:         Instruktion: \n\n";
		
		String[] ingridienser = recipe.getIngredients(); 
		for(String ingr : ingridienser){
			recepyInfo += ingr + "\n";
		}
		
		text.setText(recepyInfo);
		Text instruction = new Text();
		instruction.setText(recipe.getInstruction());
		
		instruction.setX(140);
		instruction.setY(230);
		
		title.setY(50);
		title.setX(10);
		text.setY(100);
		text.setX(10);
		anchorpane.getChildren().add(title);
		anchorpane.getChildren().add(text);
		anchorpane.getChildren().add(instruction);
	}
}