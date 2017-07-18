package ua.taxistation.controller.command.realization.dispatcher;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.taxistation.controller.command.Command;
import ua.taxistation.controller.constants.LocaleEnum;
import ua.taxistation.controller.constants.Page;
import ua.taxistation.controller.constants.Parameters;
import ua.taxistation.entity.Request;
import ua.taxistation.services.RequestService;
import ua.taxistation.utilities.LocaleManager;

public class GetAllRequestsCommand implements Command {

	private RequestService requestService;

	public GetAllRequestsCommand(RequestService requestService) {
		this.requestService = requestService;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Locale chosenLocale = LocaleEnum.getLocaleByLang((String) request.getSession().getAttribute(Parameters.LANG));
		LocaleManager.setResourceBundleLocale(chosenLocale);
		
		List<Request> requestList = requestService.getUnprocessedRequests();
		request.setAttribute(Parameters.REQUEST_LIST, requestList);
		if (request.getParameter(Parameters.SUCCESS)!=null)
			request.setAttribute(Parameters.SUCCESS, request.getParameter(Parameters.SUCCESS));
		return Page.REQUESTS_PAGE;
	}

}
