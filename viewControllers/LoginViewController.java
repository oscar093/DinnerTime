package viewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import client.Client;
import client.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import module.Login;

/**
 * Class handles communication between gui and client.
 * 
 * @author David, Oscar
 */
public class LoginViewController implements Initializable  {
	@FXML
	private Main main;
	@FXML
	private TextField username;
	@FXML
	private PasswordField password = new PasswordField();
	@FXML
	private Button login;
	@FXML
	private Button register;
	
	private Client client;
	
	private Login userLogin;
	
	private String loginStatus;
	
	@FXML
	private Label loginInfo;
	
	/**
	 * Initializes login view.
	 * 
	 * @author Oscar
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loginInfo.setVisible(false);
		password.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode() == KeyCode.ENTER){
					try {
						login();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
			
		});
	}
	
	/**
	 * 
	 * Reads user input and tries to login to server.
	 * @author Oscar, David
	 * @throws IOException
	 */
	@FXML
	public void login() throws IOException {
		loginStatus = "timeout";
		loginInfo.setVisible(false);
		
//		Mot remote server
//		client = new Client("146.148.4.203", 3250, this);
		
//		Mot lokal server
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

	/**
	 * Show register view.
	 * @throws IOException
	 */
	@FXML
	public void register() throws IOException {
		main.showRegisterView();
	}
	
	/**
	 * Get login status.
	 * Should be success if login 'seceded', 
	 * 'failed' if not. 
	 * 'timeout' if there is no connection.
	 * @return
	 */
	public String getLoginStatus() {
		return loginStatus;
	}
	
	/**
	 * Set login status.
	 * Should be success if login 'seceded', 
	 * 'failed' if not. 
	 * 'timeout' if there is no connection.
	 * @param loginStatus
	 */
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
}