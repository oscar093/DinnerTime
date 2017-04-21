package dinnerTime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		TreeItem<String> root = new TreeItem<>();
		
		// Africa
		TreeItem<String> africa = new TreeItem<>("Africa");
		TreeItem<String> kenya = new TreeItem<>("Kenya", new ImageView(kenyaImage));
		TreeItem<String> morocco = new TreeItem<>("Morocco", new ImageView(moroccoImage));
		
		// Asia
		TreeItem<String> asia = new TreeItem<>("Asia");
		TreeItem<String> china = new TreeItem<>("China", new ImageView(chinaImage));
		TreeItem<String> japan = new TreeItem<>("Japan", new ImageView(japanImage));
		TreeItem<String> thailand = new TreeItem<>("Thailand", new ImageView(thailandImage));
		
		// Europe
		TreeItem<String> europe = new TreeItem<>("Europe");
		TreeItem<String> france = new TreeItem<>("France", new ImageView(franceImage));
		TreeItem<String> italy = new TreeItem<>("Italy", new ImageView(italyImage));
		TreeItem<String> sweden = new TreeItem<>("Sweden", new ImageView(swedenImage));
		
		// Middle East
		TreeItem<String> middleeast = new TreeItem<>("Middle East");
		TreeItem<String> iran = new TreeItem<>("Iran", new ImageView(iranImage));
		TreeItem<String> lebanon = new TreeItem<>("Lebanon", new ImageView(lebanonImage));
		
		// North America
		TreeItem<String> northAmerica = new TreeItem<>("North America");
		TreeItem<String> mexico = new TreeItem<>("Mexico", new ImageView(mexicoImage));
		TreeItem<String> usa = new TreeItem<>("USA", new ImageView(usaImage));
		
		// South America
		TreeItem<String> southAmerica = new TreeItem<>("South America");
		TreeItem<String> argentina = new TreeItem<>("Argentina", new ImageView(argentinaImage));
		TreeItem<String> colombia = new TreeItem<>("Colombia", new ImageView(colombiaImage));
		
		// Adding nodes
		root.getChildren().add(africa);
		root.getChildren().add(asia);
		root.getChildren().add(europe);
		root.getChildren().add(middleeast);
		root.getChildren().add(northAmerica);
		root.getChildren().add(southAmerica);
		
		africa.getChildren().add(kenya);
		africa.getChildren().add(morocco);
		
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
		
		treeview.setRoot(root);
		treeview.setShowRoot(false);
	}
	
	public void setClient(Client client) {
		this.client = client;
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
}