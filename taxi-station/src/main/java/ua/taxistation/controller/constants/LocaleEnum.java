package ua.taxistation.controller.constants;

import java.util.Locale;

public enum LocaleEnum {

	EN(new Locale("en", "GB")), RU(new Locale("ru", "RU"));

	private final static Locale DEFAULT_LOCALE = EN.getLocale();

	private Locale locale;

	LocaleEnum(Locale locale) {
		this.locale = locale;
	}

	public Locale getLocale() {
		return locale;
	}

	public static Locale getDefault() {
		return DEFAULT_LOCALE;
	}
}
