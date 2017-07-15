package ua.taxistation.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.command.CommandFactory;
import ua.taxistation.controller.utilities.RedirectionManager;
import ua.taxistation.exceptions.ServerAppException;

@WebServlet(urlPatterns = "/main/*")
public class Taxistation extends HttpServlet {

	// private static final Logger LOGGER = Logger.getLogger(Taxistation.class);
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

		Command command = CommandFactory.getCommand(request);
		try {
			String resultPath = command.execute(request, response);
			if (!resultPath.equals(RedirectionManager.REDIRECTION))
				request.getRequestDispatcher(resultPath).forward(request, response);
		} catch (Exception e) {
			//redirecToHomePageWithErrorMessage(request,response, e);
		}

	}

	// private void redirecToHomePageWithErrorMessage(HttpServletRequest request,
	// HttpServletResponse response, ServerAppException e) throws IOException {
	// Map<String, String> urlParams = new HashMap<>();
	// urlParams.put(Attribute.ERROR, ex.getMessage());
	// RedirectionManager.getInstance().redirectWithParams(httpWrapper,
	// ServletPath.HOME, urlParams);
	// }
}
