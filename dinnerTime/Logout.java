package dinnerTime;

import java.io.Serializable;

/**
 * Logout class with information of who is logging out.
 *
 * @author David
 */
public class Logout implements Serializable {
	private static final long serialVersionUID = -8884097561060840928L;
	private String username;
	
	/**
	 * Constructor with username of client to log out.
	 * 
	 * @param username
	 */
	public Logout(String username) {
		this.username = username;
	}
	
	/**
	 * Get username of client.
	 * 
	 * @return username.
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Set username of client.
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}