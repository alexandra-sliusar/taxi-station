package ua.taxistation.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.command.CommandFactory;
import ua.taxistation.controller.constants.LocaleEnum;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.controller.utilities.RedirectionManager;
import ua.taxistation.exceptions.ServerAppException;
import ua.taxistation.utilities.LocaleManager;

@WebServlet(urlPatterns = { "/main/*" })
public class Taxistation extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger(Taxistation.class);
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		initializePage(request, response);
		Command command = CommandFactory.getCommand(request);
		try {
			String resultPath = command.execute(request, response);
			if (!resultPath.equals(RedirectionManager.REDIRECTION))
				request.getRequestDispatcher(resultPath).forward(request, response);
		} catch (ServerAppException e) {
			errorPageRedirection(request, response, e);
		}
	}

	private void initializePage(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Locale chosenLocale = LocaleEnum.getLocaleByLang((String) request.getSession().getAttribute(Parameters.LANG));
		LocaleManager.setResourceBundleLocale(chosenLocale);
	}

	private void errorPageRedirection(HttpServletRequest request, HttpServletResponse response, Exception e)
			throws IOException {
		LOGGER.error("Command execution error: " + e);
		Map<String, String> message = new HashMap<>();
		message.put(Parameters.ERROR, e.getMessage());
		RedirectionManager.getInstance().redirectWithMessages(request, response, Page.MAIN_PATH, message);
	}
}
