package ua.taxistation.utilities;

import java.util.Locale;
import java.util.ResourceBundle;

public final class LocaleManager {

	public static String MESSAGES_BUNDLE_NAME = "/i18n/messages";
	public static ResourceBundle BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, new Locale("en", "GB"));

	public static void setResourceBundleLocale(Locale locale) {
		BUNDLE = ResourceBundle.getBundle(MESSAGES_BUNDLE_NAME, locale);
	}

	public static String INVALID_DATA_AUTH = "taxistation.login.data.error";
	public static String WRONG_DATA_AUTH = "taxistation.login.auth.error";
	public static String CONFIRM_PASS_ERROR = "taxistation.signup.confirmpass.error";
	public static String LOGIN_REGEX_ERROR = "taxistation.signup.login.error";
	public static String PASS_REGEX_ERROR = "taxistation.signup.pass.error";
	public static String PHONE_NUMBER_REGEX_ERROR = "taxistation.signup.phonenumber.error";
	public static String SIGNUP_DB_ERROR = "taxistation.signup.db.error";
}