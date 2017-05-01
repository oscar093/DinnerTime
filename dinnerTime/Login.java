package dinnerTime;

import java.io.Serializable;

public class Login implements Serializable {
	private static final long serialVersionUID = -6041110511032037476L;
	private String username;
	private String password;
	private String loginStatus;
	
	public Login(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public Login(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getLoginStatus() {
		return loginStatus;
	}
	
	public void setVerifyLogin(String loginStatus) {
		this.loginStatus = loginStatus;
	}
}