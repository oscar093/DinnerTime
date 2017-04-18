package dinnerTime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RegisterViewController implements Initializable {
	
	ObservableList<String> regionList = FXCollections.observableArrayList("Africa", "Asia", "Europe", "Middle East", "North America", "South America");
	ObservableList<String> africaCountryList = FXCollections.observableArrayList("Kenya", "Morocco");
	ObservableList<String> asiaCountryList = FXCollections.observableArrayList("China", "Japan", "Thailand");
	ObservableList<String> europeCountryList = FXCollections.observableArrayList("France", "Italy", "Sweden");
	ObservableList<String> middleeastCountryList = FXCollections.observableArrayList("Iran", "Lebanon");
	ObservableList<String> northAmericaCountryList = FXCollections.observableArrayList("Mexico", "USA");
	ObservableList<String> southAmericaCountryList = FXCollections.observableArrayList("Argentina", "Colombia");
	
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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		region.setItems(regionList);
	}
	
	@FXML
	private void regionChoice() {
		if(region.getValue().equals("Africa")) {
			country.setItems(africaCountryList);
		}
		else if(region.getValue().equals("Asia")) {
			country.setItems(asiaCountryList);
		}
		else if(region.getValue().equals("Europe")) {
			country.setItems(europeCountryList);
		}
		else if(region.getValue().equals("Middle East")) {
			country.setItems(middleeastCountryList);
		}
		else if(region.getValue().equals("North America")) {
			country.setItems(northAmericaCountryList);
		}
		else if(region.getValue().equals("South America")) {
			country.setItems(southAmericaCountryList);
		}
	}
	
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
	
	@FXML
	private void back() throws IOException {
		main.showLoginView();
	}
	
	public String getRegisterStatus() {
		return registerStatus;
	}
	
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
}