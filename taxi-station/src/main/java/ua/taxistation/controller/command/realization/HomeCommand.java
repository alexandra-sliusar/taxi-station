package ua.taxistation.controller.command.realization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.utilities.LocaleManager;
import ua.taxistation.utilities.LocaleMessage;

public class HomeCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String success = "";
		String error = "";
		try {
			success = request.getParameter(Parameters.SUCCESS);
			error = request.getParameter(Parameters.ERROR);
			request.setAttribute(Parameters.SUCCESS, success);
			request.setAttribute(Parameters.ERROR, error);
		} catch (Exception e) {
			request.setAttribute(Parameters.ERROR, LocaleManager.getString(LocaleMessage.ERROR));
		}
		return "/index.jsp";
	}

}
