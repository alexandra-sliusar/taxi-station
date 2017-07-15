package ua.taxistation.controller.command.realization;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.LocaleEnum;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.utilities.LocaleManager;

public class ChangeLocaleCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		setLocale(request);
		return Page.HOME_PAGE;
	}

	private void setLocale(HttpServletRequest request) {
		String selectedLanguage = request.getParameter(Parameters.LANG);
		Locale chosenLocale = LocaleEnum.valueOf(selectedLanguage).getLocale();

		request.getSession().setAttribute(Parameters.LOCALE, chosenLocale);
		LocaleManager.setResourceBundleLocale(chosenLocale);
	}
}