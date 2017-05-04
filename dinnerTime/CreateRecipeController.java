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
 * The GUI for creating a new recipe
 * @author Group 26
 *
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taIngredients.setEditable(false);
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

	@FXML
	private void addPicture() {
		if (tfPicture != null) {
			imgFileName = tfPicture.getText();
			tfPicture.setText("");
		}
	}

	public void setClient(Client client) {
		this.client = client;
	}
}