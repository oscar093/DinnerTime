package dinnerTime;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
	
	@FXML
	private void login() throws IOException {
		main.showClientView();
	}
	
	@FXML
	private void register() {
		
	}
}