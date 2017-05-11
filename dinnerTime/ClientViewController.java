package dinnerTime;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.swing.JLabel;

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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextBoundsType;

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
	
	/**
	 * Initialize and show GUI for client.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		africa = new TreeItem<String>("Africa");
		countryItemList.add(africa);
		addItems("Africa");
		
		asia = new TreeItem<String>("Asia");
		countryItemList.add(asia);
		addItems("Asia");
		
		europe = new TreeItem<String>("Europe");
		countryItemList.add(europe);
		addItems("Europe");
		
		middleEast = new TreeItem<String>("Middle East");
		countryItemList.add(middleEast);
		addItems("MiddleEast");
		
		northAmerica = new TreeItem<String>("North America");
		countryItemList.add(northAmerica);
		addItems("NorthAmerica");
		
		southAmerica = new TreeItem<String>("South America");
		countryItemList.add(southAmerica);
		addItems("SouthAmerica");
		
		root.getChildren().add(africa);
		addChildren("Africa",africa);
		
		root.getChildren().add(asia);
		addChildren("Asia",asia);
		
		root.getChildren().add(europe);
		addChildren("Europe",europe);
		
		root.getChildren().add(middleEast);
		addChildren("MiddleEast",middleEast);
		
		root.getChildren().add(northAmerica);
		addChildren("NorthAmerica",northAmerica);
		
		root.getChildren().add(southAmerica);
		addChildren("SouthAmerica",southAmerica);
		
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
	
	/**
	 * Add regions to TreeView. 
	 * @param region
	 */
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
	
	/**
	 * Add countries to treeView.
	 * @param regionString
	 * @param regionItem
	 */
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
	 * @author Oscar
	 * Precents the recipes in the TextArea.
	 * @param recipe
	 */
	public void presentRecipe(Recipe recipe){
		anchorpane.getChildren().clear();
		Text title = new Text();
		title.setText(recipe.getTitle().substring(0,1).toUpperCase() + recipe.getTitle().substring(1));
		title.setFont(Font.font ("Verdana",FontWeight.BOLD, 36));
		Text text = new Text();
		text.setFont(Font.font ("Verdana", 16));
		Text instructionTitle = new Text();
		JLabel jlb = new JLabel();
		instructionTitle.setText("Instruction");
		instructionTitle.setFont(Font.font ("Verdana",  FontWeight.BOLD , 16));

		
		if(recipe.getImg() != null){
			ByteArrayInputStream in = new ByteArrayInputStream(recipe.getImg());
			Image img = new Image(in);
			ImageView vfoodPic = new ImageView(img);
			vfoodPic.setY(70);
			vfoodPic.setX((anchorpane.getWidth()/2) - 249.5);//249.5 ska vara halva bilden.
			anchorpane.getChildren().add(vfoodPic);
			
		}
		
		String recepyInfo = "Ursprung: " + recipe.getCountry().substring(0,1).toUpperCase() + recipe.getCountry().substring(1)
							+ "\nFörfattare: " + recipe.getAuthor().substring(0,1).toUpperCase() + recipe.getAuthor().substring(1)
							+ "\n\nTillagningstid: " + recipe.getTime() + " minuter\n";
		
		String[] ingridienser = recipe.getIngredients(); 
		for(String ingr : ingridienser){
			recepyInfo += ingr + "\n";
		}
		
		text.setText(recepyInfo);
		Text instruction = new Text();
		String strInst = recipe.getInstruction();
		instruction.setText(recipe.getInstruction());
		
		instruction.setX(15);
		instruction.setY(70 + 312 + 15 + text.getLayoutBounds().getHeight() + 35);
		
		title.setY(40);
		title.setX((anchorpane.getWidth()/2) - (title.getLayoutBounds().getWidth()/2));
		text.setY(70 + 312 + 25);
		text.setX(15);
		
		instructionTitle.setY(70 + 312 + 15 + text.getLayoutBounds().getHeight() + 15);
		instructionTitle.setX(15);
		instructionTitle.setBoundsType(TextBoundsType.LOGICAL);
		
		anchorpane.getChildren().add(title);
		anchorpane.getChildren().add(text);
		anchorpane.getChildren().add(instruction);
		anchorpane.getChildren().add(instructionTitle);
	}
}