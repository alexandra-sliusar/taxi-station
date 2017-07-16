package ua.taxistation.entity.enums;

public enum OrderStatus {
	COMPLETE("enum.orderstatus.complete"), INCOMPLETE("enum.orderstatus.incomplete");
	private String localeKey;
	private OrderStatus(String localeKey) {
		this.localeKey = localeKey;
	}
	public String getLocaleKey() {
		return localeKey;
	}
}
