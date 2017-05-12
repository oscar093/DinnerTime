package module;

import java.io.Serializable; 
/**
 * Class contains all information required to register a new member.
 * 
 * @author David, Oscar
 */
public class Register implements Serializable {
	private static final long serialVersionUID = 6183615101241174093L;
	private String username;
	private String password;
	private String firstname;
	private String surname;
	private String region;
	private String country;
	private String registerStatus;
	
	/**
	 * Constructor.
	 * @param username
	 * @param password
	 * @param firstname
	 * @param surname
	 * @param region
	 * @param country
	 */
	public Register(String username, String password, String firstname, String surname, String region, String country) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.surname = surname;
		this.region = region;
		this.country = country;
	}
	
	/**
	 * Constructor.
	 * @param registerStatus
	 */
	public Register(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	/**
	 * Get username.
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set username.
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get password.
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * set password.
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get first name
	 * @return
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * Set first name
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * Get surname.
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Set surname.
	 * @param surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Get region of which the user is from.
	 * @return
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * Set region of which the user is from.
	 * @param region
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * Get country of shich the user is from.
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Set country of which the user is from.
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Get register status.
	 * Returns 'success' if successful 'failed' if not. 
	 * 'timeout' if no connection is fount whiten reasonable time.
	 * @return register status
	 */
	public String getRegisterStatus() {
		return registerStatus;
	}
	
	/**
	 * Set register status.
	 * 	Set 'success' if successful 'failed' if not. 
	 * 'timeout' if no connection is fount whiten reasonable time.
	 * @param registerStatus
	 */
	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}
}