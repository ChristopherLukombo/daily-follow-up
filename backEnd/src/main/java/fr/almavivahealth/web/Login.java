package fr.almavivahealth.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Login object for storing a user's credentials.
 * 
 * @author christopher
 *
 */
public class Login {

	@NotNull
	@Size(min = 1, max = 50)
	private String username;

	@NotNull
	@Size(min = 4, max = 100)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}