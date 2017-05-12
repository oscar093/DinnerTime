package viewControllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import client.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import module.Register;

/** 
 * Controller class for the register view. 
 * 
 * @author David
 */
public class RegisterViewController implements Initializable {
	
	ObservableList<String> regionList = FXCollections.observableArrayList("Africa", "Asia", "Europe", "Middle East", "North America", "South America");
	
	@FXML
	private Main main;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TextField firstname;
	@FXML
	private TextField surname;
	@FXML
	private ComboBox<String> region;
	@FXML
	private ComboBox<String> country;
	@FXML
	private Button register;
	@FXML
	private Button back;
	
	private Client client;
	
	private Register userRegister;
	
	private String registerStatus;
	
	@FXML
	private Label registerInfo;
	
	
	 
	/**
	 * Gives all countries to the combobox.
	 * @param URL and ResourceBundle is not used.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		region.setItems(regionList);
	}
	
	/**
	 * @author Olof
	 *
	 * the addCountries method adds different countries depending on the chosen region
	 */
	@FXML
	private void regionChoice() {
		if(region.getValue().equals("Africa")) {
			addCountries("Africa");
		}
		else if(region.getValue().equals("Asia")) {
			addCountries("Asia");
		}
		else if(region.getValue().equals("Europe")) {
			addCountries("Europe");
		}
		else if(region.getValue().equals("Middle East")) {
			addCountries("MiddleEast");
		}
		else if(region.getValue().equals("North America")) {
			addCountries("NorthAmerica");
		}
		else if(region.getValue().equals("South America")) {
			addCountries("SouthAmerica");
		}
	}
	
	/**
	 * reads and adds the countries from the txtFile with the same name as the region
	 * 
	 * @author Olof
	 * @param region : the chosen region
	 */
	private void addCountries(String region){
		country.getItems().clear();
		try {
			BufferedReader br = new BufferedReader(new FileReader("src/txtFiles/" + region.toLowerCase() + ".txt"));
			String strLine = br.readLine();
			while (strLine != null) {
				country.getItems().add(strLine);
				strLine = br.readLine();
			}
		} catch (IOException e) {
		}
	}
	
	/** 
	 * Method reads user input and tries to register person.
	 * Username must be unique.
	 * 
	 * @author David.
	 * @throws IOException
	 */
	@FXML
	private void register() throws IOException {
		registerStatus = "timeout";
		registerInfo.setVisible(false);
		client = new Client("127.0.0.1", 3250, this);
		userRegister = new Register(username.getText(), password.getText(), firstname.getText(), surname.getText(), region.getValue(), country.getValue());
		client.setOnConnected(() -> client.sendToServer(userRegister));
		client.start();
		int waitForRegisterStatusCounter = 0;
		while(registerStatus.equals("timeout")) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waitForRegisterStatusCounter++;
			if(waitForRegisterStatusCounter >= 20) {
				break;
			}
		}
		if(registerStatus.equals("success")) {
			main.showClientView(client);
			client.setUsername(username.getText());
		}
		else if(registerStatus.equals("failed")) {
			registerInfo.setVisible(true);
			registerInfo.setText("Username already in use");
		}
		else {
			registerInfo.setVisible(true);
			registerInfo.setText("Timeout: Check connection");
		}		
	}
	
	/**
	 * Returns to login view.
	 * @throws IOException
	 */
	@FXML
	private void back() throws IOException {
		main.showLoginView();
	}
	
	/**
	 * Get register status.
	 * Returns 'success' if successful 'failed' if not. 
	 * 'timeout' if no connection is fount whiten reasonable time.
	 * @return register status
	 */
	public String getRegisterStatus() {
		return registerStatus;
	}
	
	/**
	 * Set register status.
	 * 	Set 'success' if successful 'failed' if not. 
	 * 'timeout' if no connection is fount whiten reasonable time.
	 * @param registerStatus
	 */
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
}