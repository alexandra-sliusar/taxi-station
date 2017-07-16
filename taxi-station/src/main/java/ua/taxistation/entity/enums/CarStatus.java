package ua.taxistation.entity.enums;

public enum CarStatus {
	BROKEN("enum.carstatus.broken"), AVAILABLE("enum.carstatus.available"), UNAVAILABLE("enum.carstatus.unavailable");

	private String localeKey;

	CarStatus(String localeKey) {
		this.localeKey = localeKey;
	}

	public String getLocaleKey() {
		return localeKey;
	}
}
