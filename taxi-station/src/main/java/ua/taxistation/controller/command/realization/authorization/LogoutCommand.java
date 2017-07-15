package ua.taxistation.controller.command.realization.authorization;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;

public class LogoutCommand implements Command {
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return Page.HOME_PAGE;
	}
}