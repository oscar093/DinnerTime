package dinnerTime;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ClientViewController implements Initializable {
	
	@FXML
	private Main main;
	
	@FXML
	private TreeView<String> treeview;
	
	@FXML
	private Button logout;
	
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
	
	private TreeItem<String> kenya;
	private TreeItem<String> marocco;
	private TreeItem<String> africa;
	private TreeItem<String> asia;
	private TreeItem<String> china;
	private TreeItem<String> japan;
	private TreeItem<String> thailand;
	private TreeItem<String> europe;
	private TreeItem<String> france;
	private TreeItem<String> italy;
	private TreeItem<String> sweden;
	private TreeItem<String> middleeast;
	private TreeItem<String> iran;
	private TreeItem<String> lebanon;
	private TreeItem<String> northAmerica;
	private TreeItem<String> mexico;
	private TreeItem<String> usa;
	private TreeItem<String> southAmerica;
	private TreeItem<String> argentina;
	private TreeItem<String> colombia;
	
	private ArrayList<TreeItem<String>> countryItemList = new ArrayList<TreeItem<String>>();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TreeItem<String> root = new TreeItem<>();
		
		// Africa
		africa = new TreeItem<>("Africa");
		countryItemList.add(africa);
		kenya = new TreeItem<>("Kenya", new ImageView(kenyaImage));	
		countryItemList.add(kenya);
		marocco = new TreeItem<>("Marocco", new ImageView(moroccoImage));
		countryItemList.add(marocco);
		// Asia
		asia = new TreeItem<>("Asia");
		countryItemList.add(asia);
		china = new TreeItem<>("China", new ImageView(chinaImage));
		countryItemList.add(china);
		japan = new TreeItem<>("Japan", new ImageView(japanImage));
		countryItemList.add(japan);
		thailand = new TreeItem<>("Thailand", new ImageView(thailandImage));
		countryItemList.add(thailand);
		
		// Europe
		europe = new TreeItem<>("Europe");
		countryItemList.add(europe);
		france = new TreeItem<>("France", new ImageView(franceImage));
		countryItemList.add(france);
		italy = new TreeItem<>("Italy", new ImageView(italyImage));
		countryItemList.add(italy);
		sweden = new TreeItem<>("Sweden", new ImageView(swedenImage));
		countryItemList.add(sweden);
		
		// Middle East
		middleeast = new TreeItem<>("Middle East");
		countryItemList.add(middleeast);
		iran = new TreeItem<>("Iran", new ImageView(iranImage));
		countryItemList.add(iran);
		lebanon = new TreeItem<>("Lebanon", new ImageView(lebanonImage));
		countryItemList.add(lebanon);
		
		// North America
		northAmerica = new TreeItem<>("North America");
		countryItemList.add(northAmerica);
		mexico = new TreeItem<>("Mexico", new ImageView(mexicoImage));
		countryItemList.add(mexico);
		usa = new TreeItem<>("USA", new ImageView(usaImage));
		countryItemList.add(usa);
		
		// South America
		southAmerica = new TreeItem<>("South America");
		countryItemList.add(southAmerica);
		argentina = new TreeItem<>("Argentina", new ImageView(argentinaImage));
		countryItemList.add(argentina);
		colombia = new TreeItem<>("Colombia", new ImageView(colombiaImage));
		countryItemList.add(colombia);
		
		// Adding nodes
		root.getChildren().add(africa);
		root.getChildren().add(asia);
		root.getChildren().add(europe);
		root.getChildren().add(middleeast);
		root.getChildren().add(northAmerica);
		root.getChildren().add(southAmerica);
		
		africa.getChildren().add(kenya);
		africa.getChildren().add(marocco);
		
		asia.getChildren().add(china);
		asia.getChildren().add(japan);
		asia.getChildren().add(thailand);
		
		europe.getChildren().add(france);
		europe.getChildren().add(italy);
		europe.getChildren().add(sweden);
		
		middleeast.getChildren().add(iran);
		middleeast.getChildren().add(lebanon);
		
		northAmerica.getChildren().add(mexico);
		northAmerica.getChildren().add(usa);
		
		southAmerica.getChildren().add(argentina);
		southAmerica.getChildren().add(colombia);
		
		treeview.getSelectionModel().selectedItemProperty().addListener(til);
		treeview.setRoot(root);
		treeview.setShowRoot(false);
	}
	
	public void setClient(Client client) {
		this.client = client;
		client.setClientViewController(this);
	}
	
	@FXML
	public void logout() throws IOException {
		userLogout = new Logout(username);
		client.sendToServer(userLogout);
		main.showLoginView();
	}
	
	@FXML
	public void myKitchen() throws IOException{
		main.showMyKitchenView(client);
	}
	
	/**
	 * Listens for operations in the TreeView.
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
				Recipe selectedRecipe = client.getRecipe(selectedItem.getValue());
				if(selectedRecipe != null){
					//Här gör man något så att receptet visas i fönstret till höger om treeview.
					//'selctedRecipe' är självaste receptet.
					System.out.println(selectedRecipe.getAuthor() + " har lagat " + selectedRecipe.getTitle());
				}
			}
		}
	}

	/**
	 * Updated all country nodes with the latest recipes based on that particular country.
	 * @param recipe - is a Recipe array.
	 */
	public void updateNode(Recipe[] recipe) {
		if(recipe.length>0){
			for(TreeItem<String> ti : countryItemList){
				for (Recipe r : recipe){
					if(ti.getValue().toLowerCase().contentEquals(r.getCountry())){
						TreeItem<String> title = new TreeItem<String>(r.getTitle());
						ti.getChildren().add(title);
					}
				}
			}
		}
	}
}