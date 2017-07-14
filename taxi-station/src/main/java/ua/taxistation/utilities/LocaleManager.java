package ua.taxistation.utilities;

import java.util.Locale;
import java.util.ResourceBundle;

public final class LocaleManager {

	public static String MESSAGES_BUNDLE_NAME = "/i18n/messages";
	public static ResourceBundle BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, new Locale("en", "GB"));

	public static void setResourceBundleLocale(Locale locale) {
		BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, locale);
	}
}