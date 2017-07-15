package ua.taxistation.controller.command.realization.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.Page;

public class GetOrderCarCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return Page.ORDERCAR_PAGE;
	}

}
