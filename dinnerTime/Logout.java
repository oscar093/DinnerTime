package dinnerTime;

import java.io.Serializable;

public class Logout implements Serializable {
	private static final long serialVersionUID = -8884097561060840928L;
	private String username;
	
	public Logout(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
}