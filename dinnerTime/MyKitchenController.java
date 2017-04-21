package dinnerTime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.*;

public class MyKitchenController implements Initializable {
	private static Stage primaryStage;
	private AnchorPane pane;
	private Client client;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

	@FXML
	public void newRecipe() throws IOException{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("CreateRecipeView.fxml"));
			pane = loader.load();
			
			CreateRecipeController crc = loader.getController();
			crc.setClient(client);
			
			Stage addDialogStage = new Stage();
			addDialogStage.setTitle("New Recipe!");
			addDialogStage.initModality(Modality.WINDOW_MODAL);
			addDialogStage.initOwner(primaryStage);
			
			Scene scene = new Scene(pane);
			addDialogStage.setScene(scene);
			addDialogStage.showAndWait();
	}
	
	public void setClient(Client client){
		this.client = client;
	}
}
