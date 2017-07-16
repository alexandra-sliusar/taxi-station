package ua.taxistation.entity.enums;

public enum Role {
	CLIENT("enum.role.client"), DISPATCHER("enum.role.dispatcher"), DRIVER("enum.role.driver");

	private String localeKey;

	Role(String localeKey) {
		this.localeKey = localeKey;
	}

	public String getLocaleKey() {
		return localeKey;
	}
}
