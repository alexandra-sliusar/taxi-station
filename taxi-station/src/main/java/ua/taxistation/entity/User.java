package ua.taxistation.entity;

import java.io.Serializable;

import ua.taxistation.entity.enums.Role;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String login;
	private String password = "";
	private String phonenumber;
	private Role role;
	private byte[] salt;

	public User() {

	}

	public static class Builder {
		protected User user = new User();

		public Builder() {
		}

		public Builder setId(Long id) {
			user.setId(id);
			return this;
		}

		public Builder setLogin(String login) {
			user.setLogin(login);
			return this;
		}

		public Builder setPassword(String password) {
			user.setPassword(password);
			return this;
		}

		public Builder setPhonenumber(String phonenumber) {
			user.setPhonenumber(phonenumber);
			return this;
		}

		public Builder setRole(Role role) {
			user.setRole(role);
			return this;
		}

		public Builder setSalt(byte[] salt) {
			user.setSalt(salt);
			return this;
		}

		public User build() {
			return user;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result += prime * result + ((login == null) ? 0 : login.hashCode());
		result += prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", password=" + password
				+ ", phonenumber=" + phonenumber + ", role=" + role + "]";

	}
}
