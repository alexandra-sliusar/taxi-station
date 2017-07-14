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

	/*
	 * public static Role forValue(String value) { for (Role role :
	 * Role.values()) { if (role.getValue().equals(value)) { return role; } }
	 * throw new RuntimeException("Role with such string value doesn't exist");
	 * }
	 */
}
