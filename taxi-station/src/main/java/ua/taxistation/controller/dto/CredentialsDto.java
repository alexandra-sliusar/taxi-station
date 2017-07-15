package ua.taxistation.controller.dto;

public class CredentialsDto {
	private String login;
	private String password;
	private String confirmPassword;
	private String phonenumber;

	public CredentialsDto() {
	}

	public CredentialsDto(String login, String password, String confirmPassword, String phonenumber) {
		this.login = login;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.phonenumber = phonenumber;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "CredentialsDto [login=" + login + ", password=" + password + ", confirmPassword=" + confirmPassword
				+ ", phonenumber=" + phonenumber + "]";
	}
}
