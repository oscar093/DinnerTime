package viewControllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import client.ImageResizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import module.Recipe;

/**
 * Controller class for createRecipeView
 */

public class CreateRecipeController implements Initializable {
	@FXML
	private Button btnSend, btnAddIngredient, btnPicture;
	@FXML
	private TextField tfTitle, tfTime, tfIngredientInput, tfPicture;
	@FXML
	private TextArea taIngredients, taInstruction;
	@FXML
	private Label lblConfirmation, lblPictureConfirmation;
	@FXML
	private ComboBox<String> cbCountry;
	private Client client;
	private String imgFileName = null;

	/**
	 * @author Olof
	 * 
	 * TaIngredients is set to disabled, the only way for ingredients to be
	 * added is through the ifIngredientInput. lblConfrimation is made
	 * invisible. cbCountry adds every country by reading the txtFile
	 * Countries.txt
	 * 
	 * @param URL,
	 *            ResourceBundle, is not used.
	 */

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taIngredients.setEditable(false);
		taIngredients.setScrollLeft(0);
		lblConfirmation.setVisible(false);
		lblPictureConfirmation.setVisible(false);

		try {
			BufferedReader br = new BufferedReader(new FileReader("src/txtFiles/Countries.txt"));
			String strLine = br.readLine();
			while (strLine != null) {
				cbCountry.getItems().add(strLine);
				strLine = br.readLine();
			}
		} catch (IOException e) {
		}
	}

	/**
	 * @author Olof
	 * 
	 * If there is input in the textfields and textareas they are sent to the
	 * Recipe class. lblConfirmation is made visible.
	 */
	@FXML
	private void sendRecipe() {
		Recipe recipe = new Recipe();

		if (tfTitle.getText().equals("") || cbCountry.getValue() == null || taIngredients.getText().equals("")) {
//			lblSendError.setVisible(true);
			lblConfirmation.setText("TITLE, COUNTRY AND AT LEAST ONE INGREDIENT MUST BE GIVEN");
			lblConfirmation.setTextFill(Color.RED);
			lblConfirmation.setVisible(true);
		} else {
			recipe.setTitle(tfTitle.getText());
			recipe.setCountry(cbCountry.getValue());
			if (!tfTime.getText().equals("")) {
				try {
					recipe.setTime(Integer.parseInt(tfTime.getText()));
				} catch (NumberFormatException e) {
					recipe.setTime(0);
				}
			}
			String[] ingredients = taIngredients.getText().split("\\n");
			for (int i = 0; i < ingredients.length; i++) {
				recipe.addIngredient(ingredients[i]);
			}
			if (!taInstruction.getText().equals("")) {
				recipe.setInstruction(taInstruction.getText());
			}
			if (imgFileName != null) {
				recipe.setImgFileName(imgFileName);
			}
			recipe.setAuthor(client.getUsername());
			client.sendToServer(recipe);
			lblConfirmation.setText("RECIPE SENT!");
			lblConfirmation.setTextFill(Color.GREEN);
			lblConfirmation.setVisible(true);
		}
	}

	/**
	 * @author Olof
	 * 
	 * The text in tfIngredientInput is added to taIngredients.
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
	 * @author Olof
	 * 
	 * Removes the latest added ingredient by rewriting everything but the last
	 * row in taIngredients list.
	 */
	@FXML
	private void removeLatestIngredient() {
		String[] ingredients = taIngredients.getText().split("\n");
		String newIngredientList = "";

		for (int i = 0; i < ingredients.length - 1; i++) {
			if (i < ingredients.length - 2) {
				newIngredientList += ingredients[i] + "\n";
			} else {
				newIngredientList += ingredients[i];
			}
		}
		taIngredients.setText(newIngredientList);
	}

	/**
	 * @author Olof
	 * 
	 * Clears taIngredients.
	 */
	@FXML
	private void clearIngredients() {
		taIngredients.setText("");
	}

	/**
	 * 
	 * Resizes the chosen picture and saves the path.
	 * 
	 * @author Oscar, Olof
	 */
	@FXML
	private void addPicture() {
		if (tfPicture.getText().equals("")) {
			lblPictureConfirmation.setTextFill(Color.RED);
			lblPictureConfirmation.setText("You must choose a picture before adding!");
			lblPictureConfirmation.setVisible(true);
		}
		else{
			String tmpPath = "./tmp." + tfPicture.getText().substring(tfPicture.getText().length() - 4);
			ImageResizer ir = new ImageResizer();
			try {

				ir.resize(tfPicture.getText(), tmpPath, 499, 312);
			} catch (IOException e) {

				e.printStackTrace();
			}
			imgFileName = tmpPath;
			tfPicture.setText("");
			lblPictureConfirmation.setTextFill(Color.GREEN);
			lblPictureConfirmation.setText("Picture added!");
			lblPictureConfirmation.setVisible(true);
			btnPicture.setDisable(true);
		}
	}

	/**
	 * @author Olof
	 * 
	 * Set-method for the client. the client is needed so that the author of the
	 * recipe can be saved.
	 */
	public void setClient(Client client) {
		this.client = client;
	}
}