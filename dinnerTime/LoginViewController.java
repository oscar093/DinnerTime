package dinnerTime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * The GUI for the login frame
 * @author Group 26
 *
 */
public class LoginViewController implements Initializable {
	@FXML
	private Main main;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password;
	@FXML
	private Button login;
	@FXML
	private Button register;
	
	private Client client;
	
	private Login userLogin;
	
	private String loginStatus;
	
	@FXML
	private Label loginInfo;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginInfo.setVisible(false);
	}
	
	@FXML
	public void login() throws IOException {
		loginStatus = "timeout";
		loginInfo.setVisible(false);
		
		//Mot remote server
//		client = new Client("146.148.4.203", 3250, this);
		
		// Mot lokal server
		client = new Client("localhost", 3250, this);
		
		userLogin = new Login(username.getText(), password.getText());
		client.setOnConnected(() -> client.sendToServer(userLogin));
		client.start();
		int waitForLoginStatusCounter = 0;
		while(loginStatus.equals("timeout")) {
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			waitForLoginStatusCounter++;
			if(waitForLoginStatusCounter >= 20) {
				break;
			}
		}
		if(loginStatus.equals("success")) {
			client.setUsername(username.getText());
			main.showClientView(client);
		}
		else if(loginStatus.equals("failed")) {
			loginInfo.setVisible(true);
			loginInfo.setText("Login failed: Wrong username or password");
		}
		else {
			loginInfo.setVisible(true);
			loginInfo.setText("Timeout: Check connection");
		}
	}
	
	@FXML
	public void register() throws IOException {
		main.showRegisterView();
	}
	
	public String getLoginStatus() {
		return loginStatus;
	}
	
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
}