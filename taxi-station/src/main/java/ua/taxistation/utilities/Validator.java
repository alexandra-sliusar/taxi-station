package ua.taxistation.utilities;

public final class Validator {
	private static final String LOGIN_EMAIL_REGEX = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@"
			+ "[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,6}))?$";
	private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{5,20}$";
	private static final String ADDRESS_REGEX = "^([A-Za-z0-9\\.,/\\s]){3,}[A-Za-z0-9\\.,/\\s]+";
	private static final String PHONE_NUMBER_REGEX = "^\\(\\d{3}\\)\\s\\d{3}-\\d{2}-\\d{2}$";

	// private static final String DATETIME_PATTERN_EN = "MM.dd.yyyy hh:mm:ss
	// a";
	// private static final String DATETIME_PATTERN_UA = "dd.MM.yyyy HH:mm:ss";

	private static class Holder {
		static final Validator INSTANCE = new Validator();
	}

	public static Validator getInstance() {
		return Holder.INSTANCE;
	}

	public boolean validateLogin(String login) {
		if (login == null || !login.matches(LOGIN_EMAIL_REGEX) || login == "") {
			return false;
		}
		return true;
	}

	public boolean validatePassword(String password) {
		if (password == null || !password.matches(PASSWORD_REGEX) || password == "") {
			return false;
		}
		return true;
	}

	public boolean validateAddress(String address) {
		if (address == null || !address.matches(ADDRESS_REGEX) || address == "") {
			return false;
		}
		return true;
	}

	public boolean validatePhoneNumber(String number) {
		if (number == null || !number.matches(PHONE_NUMBER_REGEX) || number == "") {
			return false;
		}
		return true;
	}
}
