package dinnerTime;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class CreateRecipeController implements Initializable{
	private Button btnSend, btnAddIngredient;
	private TextField tfTitle, tfTime, tfCountry, tfIngredientInput;
	private TextArea taIngredients;
	private Client client;
	private Pane window;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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
		client.sendToServer(recipe);
		window.setVisible(false);
	}
	
	@FXML
	private void addIngredient(){
		if(taIngredients.getText().isEmpty()){
			taIngredients.setText(tfIngredientInput.getText());
		}
		if(!taIngredients.getText().isEmpty()){
			taIngredients.setText(taIngredients.getText() + "\n" + tfIngredientInput.getText());
		}
	}
	
	public void setClient(Client client){
		this.client = client;
	}	
}