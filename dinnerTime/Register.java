package dinnerTime;

import java.io.Serializable;

public class Register implements Serializable {
	private static final long serialVersionUID = 6183615101241174093L;
	private String username;
	private String password;
	private String firstname;
	private String surname;
	private String region;
	private String country;
	private String registerStatus;
	
	public Register(String username, String password, String firstname, String surname, String region, String country) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.surname = surname;
		this.region = region;
		this.country = country;
	}
	
	public Register(String registerStatus) {
		this.registerStatus = registerStatus;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getRegisterStatus() {
		return registerStatus;
	}
	
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
}