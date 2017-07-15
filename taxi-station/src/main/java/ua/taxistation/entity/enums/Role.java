package ua.taxistation.entity.enums;

public enum Role {
	CLIENT(""/* Message.ROLE_LIBRARIAN */), DISPATCHER(""), DRIVER("");

	private String localizedValue;

	Role(String localizedValue) {
		this.localizedValue = localizedValue;
	}

	public String getLocalizedValue() {
		return localizedValue;
	}
}
