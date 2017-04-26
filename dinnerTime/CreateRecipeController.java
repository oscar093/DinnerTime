package dinnerTime;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class CreateRecipeController implements Initializable{
	@FXML
	private Button btnSend, btnAddIngredient;
	@FXML
	private TextField tfTitle, tfTime, tfCountry, tfIngredientInput;
	@FXML
	private TextArea taIngredients, taInstruction;
	@FXML
	private Label lblConfirmation; 
	
	private Client client;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taIngredients.setEditable(false);
		lblConfirmation.setVisible(false);
	}

	@FXML
	private void sendRecipe(){
		Recipe recipe = new Recipe();
		
		if (!tfTitle.getText().isEmpty()) {
			recipe.setTitle(tfTitle.getText());
		}
		if (!tfCountry.getText().isEmpty()) {
			recipe.setCountry(tfCountry.getText());
		}
		if (!tfTime.getText().isEmpty()) {
			recipe.setTime(Integer.parseInt(tfTime.getText()));
		}
		if (!taIngredients.getText().isEmpty()) {
			String[] ingredients = taIngredients.getText().split("\\n");
			for (int i = 0; i < ingredients.length; i++) {
				recipe.setIngredient(ingredients[i]);
			}
		}
		if(!taInstruction.getText().isEmpty()){
			recipe.setInstruction(taInstruction.getText());
		}
		recipe.setAuthor(client.getUsername());
		client.sendToServer(recipe);
		lblConfirmation.setVisible(true);
	}
	
	@FXML
	private void addIngredient(){
		if(taIngredients.getText().isEmpty()){
			taIngredients.setText(tfIngredientInput.getText());
		}
		else{
			taIngredients.setText(taIngredients.getText() + "\n" + tfIngredientInput.getText());
		}
		tfIngredientInput.setText("");
	} 
	
	public void setClient(Client client){
		this.client = client;
	}
}