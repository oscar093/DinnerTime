package dinnerTime;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginViewController {
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
	
	private User user;
	
	@FXML
	public void login() throws IOException {
		client = new Client("127.0.0.1", 3250);
		user = new User(username.getText(), password.getText());
		client.setOnConnected(() -> client.sendToServer(user));
		client.start();
	}
}