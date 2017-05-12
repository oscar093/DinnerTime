package module;

import java.io.Serializable;

/**
 * Login class containt all information to check if i client can have accsess.
 * 
 * @author David
 */
public class Login implements Serializable {
	private static final long serialVersionUID = -6041110511032037476L;
	private String username;
	private String password;
	private String loginStatus;
	
	/**
	 * Constructor takes username and password of client.
	 * @param username
	 * @param password
	 */
	public Login(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Constructor taker loginstatus for client. 
	 * Should be success if login 'seceded', 
	 * 'failed' if not. 
	 * 'timeout' if there is no connection.
	 * @param loginStatus
	 */
	public Login(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	/**
	 * Get username of this login.
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Set username of this login.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get password of this login
	 * @return password.
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Set password of this login.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Get loginstatus for client. 
	 * Should be success if login 'seceded', 
	 * 'failed' if not. 
	 * 'timeout' if there is no connection.
	 * @param loginStatus
	 * @return loginstatus
	 */
	public String getLoginStatus() {
		return loginStatus;
	}
	
	/**
	 * Set loginstatus for client. 
	 * Should be success if login 'seceded', 
	 * 'failed' if not. 
	 * 'timeout' if there is no connection.
	 * @param loginStatus
	 */
	public void setVerifyLogin(String loginStatus) {
		this.loginStatus = loginStatus;
	}
}