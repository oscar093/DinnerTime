package dinnerTime;

import java.io.Serializable;

public class User implements Serializable{	//måste vara Serializable
	private String name, password;
	
	public User(String name, String password){
		this.name = name;
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name + " " + password;
	}
}