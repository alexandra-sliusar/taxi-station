package ua.taxistation.entity.enums;

public enum CarCharacteristics {
	MINIBUS("taxistation.carchar.minibus"), ANIMAL_TRANSPORTATION("taxistation.carchar.animal"), RECEIPT(
			"taxistation.carchar.receipt"), CREDITCARD_PAYMENT("taxistation.carchar.creditcard"), WIFI(
					"taxistation.carchar.wifi"), PREMIUM("taxistation.carchar.premium"), NONE("taxistation.carchar.none");
	private String localeKey;

	CarCharacteristics(String localeKey) {
		this.localeKey = localeKey;
	}

	public String getLocaleKey() {
		return localeKey;
	}
}
