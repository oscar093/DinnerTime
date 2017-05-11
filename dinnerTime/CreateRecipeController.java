package dinnerTime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

/**
 * @author Olof
 */

public class CreateRecipeController implements Initializable {
	@FXML
	private Button btnSend, btnAddIngredient, btnPicture;
	@FXML
	private TextField tfTitle, tfTime, tfCountry, tfIngredientInput, tfPicture;
	@FXML
	private TextArea taIngredients, taInstruction;
	@FXML
	private Label lblConfirmation;
	@FXML
	private ComboBox cbCountry;
	private Client client;
	private String imgFileName = null;

	/**
	 * taIngredients sätts till disabled så att användaren inte kan skriva direkt i den, enda sättet att lägga till ingredienser ska vara genom tfIngredientInput
	 * lblConfirmation görs osynlig
	 * alla länder läggs in i cbCountry från txtfilen
	 */
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taIngredients.setEditable(false);
		taIngredients.setScrollLeft(0);
		lblConfirmation.setVisible(false);
		

		try {
			BufferedReader br = new BufferedReader(new FileReader("src/Countries.txt"));
			String strLine = br.readLine();
			while (strLine != null) {
				cbCountry.getItems().add(strLine);
				strLine = br.readLine();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * Kollar så att det finns någon input i textfälten/textarea innan de skickas till recipe
	 * receptet skickas till servern
	 * lblConfirmation visas
	 */
	@FXML
	private void sendRecipe() {
		Recipe recipe = new Recipe();

		if (!tfTitle.getText().isEmpty()) {
			recipe.setTitle(tfTitle.getText());
		}
		if (cbCountry.getValue() != null) {
			recipe.setCountry(cbCountry.getValue().toString());
		}
		if (!tfTime.getText().isEmpty()) {
			try {
				recipe.setTime(Integer.parseInt(tfTime.getText()));
			} catch (NumberFormatException e) {
				recipe.setTime(0);
			}
		}
		if (!taIngredients.getText().isEmpty()) {
			String[] ingredients = taIngredients.getText().split("\\n");
			for (int i = 0; i < ingredients.length; i++) {
				recipe.addIngredient(ingredients[i]);
			}
		}
		if (!taInstruction.getText().isEmpty()) {
			recipe.setInstruction(taInstruction.getText());
		}
		if (imgFileName != null) {
			recipe.setImgFileName(imgFileName);
		}
		recipe.setAuthor(client.getUsername());
		client.sendToServer(recipe);
		lblConfirmation.setVisible(true);
	}

	
	/**
	 * texten i tfIngredientInput läggs till i taIngredients
	 */
	@FXML
	private void addIngredient() {
		if (taIngredients.getText().isEmpty()) {
			if (tfIngredientInput != null) {
				taIngredients.setText(tfIngredientInput.getText());
			}
		} else {
			if (tfIngredientInput != null) {
				taIngredients.setText(taIngredients.getText() + "\n" + tfIngredientInput.getText());
			}
		}
		tfIngredientInput.setText("");
	}
	
	/**
	 * Tar bort senaste ingrediensen genom att skriva om hela innehållet i taIngredients förutom sista raden
	 */
	@FXML
	private void removeLatestIngredient(){
		String[] ingredients = taIngredients.getText().split("\n");
		String newIngredientList = "";
		
		for(int i = 0; i < ingredients.length - 1; i++){
			newIngredientList += ingredients[i] + "\n";
		}
		
		taIngredients.setText(newIngredientList);
	}
	
	/**
	 * rensar alla ingredienser i taIngredients
	 */
	@FXML
	private void clearIngredients(){
		taIngredients.setText("");
	}

	/**
	 * filsökvägen till bilden sparas
	 */
	@FXML
	private void addPicture() {
		if (tfPicture != null) {
			String tmpPath = "./tmp." + tfPicture.getText().substring(tfPicture.getText().length() - 4);
			ImageResizer ir = new ImageResizer();
			try {
				ir.resize(tfPicture.getText(), tmpPath, 499, 312);
			} catch (IOException e) {
				e.printStackTrace();
			}
			imgFileName = tmpPath;
			tfPicture.setText("");
		}
	}

	/**
	 * klientens information sparas i instansvariablen, används för att kunna veta vem som skriver receptet
	 */
	public void setClient(Client client) {
		this.client = client;
	}
}