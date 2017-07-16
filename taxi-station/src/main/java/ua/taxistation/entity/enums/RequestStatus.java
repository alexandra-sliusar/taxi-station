package ua.taxistation.entity.enums;

public enum RequestStatus {
	PROCESSED("enum.requeststatus.processed"), UNPROCESSED("enum.requeststatus.unprocessed"), REJECTED("enum.requeststatus.rejected");
	
	private String localeKey;

	RequestStatus(String localeKey) {
		this.localeKey = localeKey;
	}

	public String getLocaleKey() {
		return localeKey;
	}
}
